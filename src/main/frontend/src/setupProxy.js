const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',
            changeOrigin: true,
            logLevel: 'debug',
            pathRewrite: {
                '^/api': '/api', // /api 경로를 그대로 유지
            },
        })
    );


};
