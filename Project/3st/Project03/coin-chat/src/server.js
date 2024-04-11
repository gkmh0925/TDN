//npm i cors
//npm i express
//npm i socket.io

const app = require('express')();
const server = require('http').createServer(app);
const io = require('socket.io')(server, {
    // cors 설정
    cors: {
        credentials: true,
    },
    allowEIO3: true,
    pingTimeout: 1000,
});

//connection event handler
io.on('connection' , function(socket) {

    socket.on('chat', function(data){
        const msg = {
            message: data.message,
            owner: data.owner
        }
        console.log(msg)
        socket.broadcast.emit('chat', msg);
    });

    socket.on('qr', function(data){
        console.log(data)

        socket.broadcast.emit('qr', data);
    });
})

server.listen(3000, function() {
    console.log('socket io server listening on port')
})