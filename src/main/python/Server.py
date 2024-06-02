import asyncio
import websockets
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(message)s')

clients = set()

async def echo(websocket, path):
    clients.add(websocket)
    try:
        async for message in websocket:
            logging.info(f"Received message: {message}")
            # Create tasks for sending messages to all clients except the sender
            tasks = [asyncio.create_task(client.send(message)) for client in clients if client != websocket]
            # Wait for all tasks to complete
            await asyncio.gather(*tasks)
    except websockets.ConnectionClosed as e:
        logging.info(f"Client disconnected: {websocket.remote_address}, Code: {e.code}, Reason: {e.reason}")
    finally:
        clients.remove(websocket)

# Start the WebSocket server
start_server = websockets.serve(echo, "localhost", 8080)

# Run the WebSocket server until the program is terminated
asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
