<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <link rel="apple-touch-icon" sizes="76x76" href="<?= ($BASE) ?>/ui/assets/img/apple-icon.png">
  <link rel="icon" type="image/png" href="<?= ($BASE) ?>/ui/assets/img/favicon.png">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>
    ArduinoVendingMachine
  </title>
  <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
  <?php echo $this->render('templates-parts/header-dependencies.htm',NULL,get_defined_vars(),0); ?> 
</head>

<body class="">
  
  <div class="wrapper ">
  	<?php echo $this->render('templates-parts/sidebar.htm',NULL,get_defined_vars(),0); ?>	
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
        <div class="container-fluid">
          <div class="navbar-wrapper">
            <a class="navbar-brand" href="#">Reporte</a>
          </div>
        </div>
      </nav>
      <!-- End Navbar -->
      <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header card-header-primary">
                  <h4 class="card-title ">Lista de ventas</h4>
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table">
                      <thead class=" text-primary">
                        <th>
                          NºVenta
                        </th>
                        <th>
                          UID de tarjeta RFID
                        </th>
                        <th>
                          Monto
                        </th>
                        <th>
                          Fecha-hora
                        </th>
                        <th>
                          Productos comprados
                        </th>
                      </thead>
                      <tbody>
                        <?php foreach (($ventas?:[]) as $venta): ?>
                            <tr>
                              <td>
                                <?= ($venta['ID_VENTA'])."
" ?>
                              </td>
                              <td>
                                <?= ($venta['UID_CARD'])."
" ?>
                              </td>
                              <td class="text-primary">
                                S/ <?= ($venta['MONTO'])."
" ?>
                              </td>
                              <td>
                                <?= ($venta['FECHA_HORA'])."
" ?>
                              </td>
                              <td>
                                <?= ($venta['PRODUCTOS'])."
" ?>
                              </td>
                            </tr>
                        <?php endforeach; ?>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    
    </div>
  </div>
 
  <?php echo $this->render('templates-parts/footer-dependencies.htm',NULL,get_defined_vars(),0); ?> 
</body>

</html>