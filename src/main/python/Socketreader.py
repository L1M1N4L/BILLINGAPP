import logging
import threading
import time
import serial
import websocket

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Serial port configuration
serial_port = 'COM4'
baud_rate = 9600

# WebSocket server URL
websocket_url = "ws://localhost:8080"

# Open serial port
ser = serial.Serial(serial_port, baud_rate)

def on_open(ws):
    logger.info("WebSocket connection established.")

def on_close(ws):
    logger.info("WebSocket connection closed.")

def send_data_to_websocket(data, ws):
    logger.info("Sending data to WebSocket: %s", data)
    ws.send(data)

def read_serial_and_send_to_websocket(ws):
    try:
        while True:
            # Read data from serial port
            data = ser.readline().strip().decode('utf-8')
            logger.info("Read from serial: %s", data)

            # Send data to WebSocket
            send_data_to_websocket(data, ws)

            # Optional: Add a delay to control the rate of sending data
            time.sleep(1)
    except Exception as e:
        logger.error("Error occurred: %s", e)
        ws.close()
        ser.close()

if __name__ == "__main__":
    try:
        # Connect to WebSocket server
        ws = websocket.WebSocketApp(websocket_url, on_open=on_open, on_close=on_close)
        ws_thread = threading.Thread(target=ws.run_forever)
        ws_thread.daemon = True
        ws_thread.start()

        # Start reading serial and sending to WebSocket
        read_serial_and_send_to_websocket(ws)
    except Exception as e:
        logger.error("Error occurred: %s", e)
