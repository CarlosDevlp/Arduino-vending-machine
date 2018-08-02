<?php 
	/*
	Ventas Controller
	Único controlador de la web que se encarga de ejecutar
	acciones cuando se solicita una ruta al servidor
	*/
	class VentasController{
		private static $db;
		//conecta a la base de datos
		static function connectarBD(){
			if(!isset(VentasController::$db))
				VentasController::$db=new DB\SQL(
				    'mysql:host=localhost;port=3306;dbname=id5407652_avmdb',
				    'id5407652_carlosdevlp',
				    '948110940'
				);	
		}

		//muestra la pantalla home
		static function showHome($f3){
			VentasController::connectarBD();
			$f3->set('clientes',VentasController::$db->exec('SELECT * FROM CLIENTE'));
			echo \Template::instance()->render('reporte_clientes.htm');
		}

		//muestra la pantalla reporte de ventas
		static function showReporteVentas($f3){
			VentasController::connectarBD();
			$f3->set('ventas',VentasController::$db->exec('SELECT * FROM VENTA'));
			echo \Template::instance()->render('reporte_ventas.htm');
		}

		//registra una venta en la base de datos y devuelvo un json en caso de éxito
		static function registrarVenta($f3){
			VentasController::connectarBD();
			$json=json_decode($f3->get('BODY'),true);
 			$uid=$json['uid'];
			$monto=$json['monto'];
			$fecha=$json['fecha'];
			$productos=$json['productos'];
 			VentasController::$db->exec('INSERT INTO VENTA (UID_CARD,MONTO,FECHA_HORA,PRODUCTOS) VALUES(:uid,:monto,:fecha,:productos)',array(':uid'=>$uid,':monto'=>$monto,':fecha'=>$fecha,
 				':productos'=>$productos));

			header('Content-Type: application/json');
			echo '{"error":0,"message":"ok"}';
		}

		//registra un cliente en la base de datos y devuelvo un json en caso de éxito
		static function registrarCliente($f3){
			VentasController::connectarBD();
			$json=json_decode($f3->get('BODY'),true);
 			$uid=$json['uid'];
			$nombre=$json['nombre'];
			$saldo=$json['saldo'];
 			VentasController::$db->exec('INSERT INTO CLIENTE (UID_CARD,NOMBRE,SALDO) VALUES(:uid,:nombre,:saldo)',array(':uid'=>$uid,':nombre'=>$nombre,':saldo'=>$saldo));

			header('Content-Type: application/json');
			echo '{"error":0,"message":"ok"}';
		}
	};
 ?>