from flask import Flask, request, jsonify
import mysql.connector

app = Flask(__name__)

# Configuração do banco de dados
db_config = {
    'host': 'localhost',
    'user': 'api_user',
    'password': 'Api@123456',
    'database': 'medicalfield'
}

def get_db_connection():
    return mysql.connector.connect(**db_config)

@app.route('/logs', methods=['POST'])
def create_log():
    data = request.json
    
    # Extrai os dados de auditoria enviados pelo Java
    professional_id = data.get('professionalId')  # Vem como null/None se não logado
    client_ip = data.get('clientIp', '127.0.0.1')
    http_method = data.get('httpMethod', 'UNKNOWN')
    route = data.get('route', 'UNKNOWN')
    status_code = data.get('statusCode', 0)
    processing_time_ms = data.get('processingTimeMs', 0)
    message = data.get('message', '')

    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        # Insere o log detalhado na tabela system_logs
        sql = """
            INSERT INTO system_logs 
            (professional_id, client_ip, http_method, route, status_code, processing_time_ms, message) 
            VALUES (%s, %s, %s, %s, %s, %s, %s)
        """
        values = (professional_id, client_ip, http_method, route, status_code, processing_time_ms, message)
        
        cursor.execute(sql, values)
        conn.commit()
        
        cursor.close()
        conn.close()
        
        return jsonify({"status": "success", "message": "Log de auditoria registrado."}), 201

    except Exception as e:
        print(f"Erro ao salvar log: {e}")
        return jsonify({"status": "error", "message": str(e)}), 500

if __name__ == '__main__':
    print("Servidor de Logs (Python) rodando na porta 8000...")
    app.run(host='0.0.0.0', port=8000)