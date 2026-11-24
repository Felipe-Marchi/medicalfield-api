const express = require('express');
const PDFDocument = require('pdfkit');
const nodemailer = require('nodemailer');

const app = express();
app.use(express.json());

// Retorna a cor em formato hexadecimal baseada na classificação de risco
function getTriageColor(classification) {
    if (!classification) return '#808080';
    const lower = classification.toLowerCase();
    
    if (lower.includes('vermelho')) return '#FF0000';
    if (lower.includes('laranja'))  return '#FF8C00';
    if (lower.includes('amarelo'))  return '#FFC000';
    if (lower.includes('verde'))    return '#00B050';
    if (lower.includes('azul'))     return '#0070C0';
    
    return '#808080';
}

// Constrói o layout visual do PDF
function buildPdfContent(doc, dto) {
    // 1. Cabeçalho
    doc.rect(0, 0, 595, 80).fill('#0B3B60'); 
    doc.fillColor('#FFFFFF')
       .fontSize(20).font('Helvetica-Bold').text('MEDICALFIELD', 50, 25)
       .fontSize(12).font('Helvetica').text('Ficha de Atendimento Médico', 50, 50);

    // 2. Dados do Paciente
    doc.fillColor('#000000'); 
    doc.moveDown(3); 
    
    doc.fontSize(14).font('Helvetica-Bold').text('Dados do Paciente');
    doc.moveTo(50, doc.y).lineTo(545, doc.y).strokeColor('#CCCCCC').stroke(); 
    doc.moveDown(0.5);

    doc.fontSize(10).font('Helvetica-Bold').text('Nome: ', { continued: true }).font('Helvetica').text(dto.patientName || 'Não informado');
    doc.font('Helvetica-Bold').text('CPF: ', { continued: true }).font('Helvetica').text(dto.patientCpf || 'Não informado');
       
    const dateStr = dto.attendanceDatetime ? new Date(dto.attendanceDatetime).toLocaleString('pt-BR') : 'Não informada';
    doc.font('Helvetica-Bold').text('Data/Hora: ', { continued: true }).font('Helvetica').text(dateStr);
    doc.moveDown(1.5);

    // 3. Triagem e Sinais Vitais
    doc.fontSize(14).font('Helvetica-Bold').text('Triagem e Sinais Vitais');
    doc.moveTo(50, doc.y).lineTo(545, doc.y).strokeColor('#CCCCCC').stroke();
    doc.moveDown(0.5);

    const triageColor = getTriageColor(dto.triageClassification);
    doc.fontSize(10).font('Helvetica-Bold').text('Classificação: ', { continued: true })
       .fillColor(triageColor).text((dto.triageClassification || 'Não classificado').toUpperCase());
    doc.fillColor('#000000'); 
    doc.moveDown(0.5);

    const startY = doc.y;
    doc.font('Helvetica-Bold').text('Escala de Glasgow: ', 50, startY, { continued: true }).font('Helvetica').text(dto.glasgowScale || '--');
    doc.font('Helvetica-Bold').text('Pressão Arterial: ', 50, startY + 15, { continued: true }).font('Helvetica').text(dto.bloodPressure || '--');
    doc.font('Helvetica-Bold').text('Freq. Cardíaca: ', 300, startY, { continued: true }).font('Helvetica').text(dto.heartRate ? dto.heartRate + ' bpm' : '--');
    doc.font('Helvetica-Bold').text('Saturação (O2): ', 300, startY + 15, { continued: true }).font('Helvetica').text(dto.oxygenSaturation ? dto.oxygenSaturation + '%' : '--');

    // 4. Prontuário e Observações
    doc.x = 50;
    doc.y = startY + 45;
    doc.fontSize(14).font('Helvetica-Bold').text('Prontuário e Observações');
    doc.moveTo(50, doc.y).lineTo(545, doc.y).strokeColor('#CCCCCC').stroke();
    doc.moveDown(0.5);
    doc.fontSize(10).font('Helvetica').text(dto.medicalRecordObservations || 'Nenhuma observação médica registrada para este atendimento.', { align: 'justify' });
    doc.moveDown(1.5);

    // 5. Medicamentos Administrados
    doc.fontSize(14).font('Helvetica-Bold').text('Medicamentos Administrados');
    doc.moveTo(50, doc.y).lineTo(545, doc.y).strokeColor('#CCCCCC').stroke();
    doc.moveDown(0.5);

    if (dto.administeredMedications) {
        try {
            const medicationsArray = JSON.parse(dto.administeredMedications);
            if (Array.isArray(medicationsArray) && medicationsArray.length > 0) {
                doc.fontSize(10).font('Helvetica');
                medicationsArray.forEach(med => {
                    if (typeof med === 'object') {
                        const details = Object.values(med).join(' - ');
                        doc.text(`• ${details}`);
                    } else {
                        doc.text(`• ${med}`);
                    }
                });
            } else {
                doc.fontSize(10).font('Helvetica').text('Nenhum medicamento registrado.');
            }
        } catch (error) {
            doc.fontSize(10).font('Helvetica').text(dto.administeredMedications, { align: 'justify' });
        }
    } else {
        doc.fontSize(10).font('Helvetica').text('Nenhum medicamento registrado.', { align: 'justify' });
    }

    doc.end();
}

// Rota para baixar o PDF diretamente no navegador
app.post('/generate-pdf', (req, res) => {
    const dto = req.body;
    const doc = new PDFDocument({ margin: 50, size: 'A4' });

    res.setHeader('Content-Type', 'application/pdf');
    res.setHeader('Content-Disposition', 'attachment; filename=ficha_atendimento.pdf');
    
    // Conecta o fluxo do documento diretamente à resposta HTTP
    doc.pipe(res);
    
    buildPdfContent(doc, dto);
});

// Rota para anexar o PDF e enviar por e-mail
app.post('/send-email', (req, res) => {
    const dto = req.body;
    const targetEmail = req.query.targetEmail;

    if (!targetEmail) {
        return res.status(400).send('E-mail de destino não informado.');
    }

    const doc = new PDFDocument({ margin: 50, size: 'A4' });
    const buffers = [];
    
    // Armazena os dados do PDF na memória
    doc.on('data', buffers.push.bind(buffers));
    
    // Dispara o e-mail após a finalização da geração do PDF
    doc.on('end', () => {
        const pdfBuffer = Buffer.concat(buffers);

        const transporter = nodemailer.createTransport({
            service: 'gmail',
            auth: {
                user: 'felipemarchi20@gmail.com', 
                pass: 'lxab erot zxvp dklu' 
            }
        });

        const mailOptions = {
            from: 'felipemarchi20@gmail.com',
            to: targetEmail,
            subject: `MedicalField - Ficha de Atendimento: ${dto.patientName || 'Paciente'}`,
            text: `Olá,\n\nSegue em anexo a ficha médica detalhada referente ao atendimento de ${dto.patientName || 'Paciente'}.\n\nAtenciosamente,\nEquipe MedicalField`,
            attachments: [
                {
                    filename: `ficha_atendimento_${(dto.patientName || 'paciente').replace(/\s+/g, '_')}.pdf`,
                    content: pdfBuffer,
                    contentType: 'application/pdf'
                }
            ]
        };

        transporter.sendMail(mailOptions, (error, info) => {
            if (error) {
                console.log(error);
                return res.status(500).send('Erro ao enviar e-mail');
            }
            res.status(200).send('E-mail enviado com sucesso');
        });
    });

    buildPdfContent(doc, dto);
});

app.listen(5000, () => {
    console.log('Servidor Node.js rodando na porta 5000...');
});