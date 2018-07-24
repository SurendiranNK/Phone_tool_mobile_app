var http = require('http');
var server = http.createServer();
var io = require('socket.io')(server);

server.listen(3000, function () {
	console.log('listening on *:3000');
});

io.sockets.on('connection', function (socket) {
	console.log('socket connected');
	
	socket.on('disconnect', function () {
		console.log('socket disconnected');
	});
	
	socket.on('sendTestReport', function (text) {
		console.log(text);
	});

});