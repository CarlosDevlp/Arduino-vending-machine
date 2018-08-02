<?php


// Kickstart the framework
$f3=require('lib/base.php');

$f3->set('DEBUG',1);
if ((float)PCRE_VERSION<7.9)
	trigger_error('PCRE version is out of date');

// Load configuration
$f3->config('config.ini');
$f3->set('AUTOLOAD','controllers/');
//rutas para devolver una página web
$f3->route('GET /','VentasController::showHome');
$f3->route('GET /reporte-ventas','VentasController::showReporteVentas');
//web service para guardar info en la base de datos
$f3->route('POST /api/cliente','VentasController::registrarCliente');
$f3->route('POST /api/venta','VentasController::registrarVenta');

$f3->run();
