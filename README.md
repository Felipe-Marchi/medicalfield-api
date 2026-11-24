# MedicalField API

Arquitetura desenvolvida para o sistema **MedicalField**, integrando gestão clínica, geração de documentos/notificações e auditoria de requisições.

## Arquitetura e Serviços

O repositório centraliza os três serviços que compõem o backend da aplicação:

* **API Principal (`/` - Raiz):** Desenvolvida em **Java (Spring Boot)**. Responsável pelas regras de negócio, autenticação (Spring Security), portal web (Thymeleaf), endpoints REST e integração com o banco de dados MySQL.
* **Serviço de Documentos e E-mail (`/medicalfield_pdf_email`):** Desenvolvido em **Node.js** com **Puppeteer**. Responsável pela renderização de relatórios em PDF e disparo de e-mails.
* **Serviço de Auditoria (`/medicalfield_logging`):** Desenvolvido em **Python (Flask)**. Recebe assincronamente os dados das requisições da API principal e persiste os logs.
