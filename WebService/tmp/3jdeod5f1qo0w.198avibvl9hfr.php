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
  <!--     Fonts and icons     -->
  <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
  <!-- CSS Files -->
  <link href="<?= ($BASE) ?>/ui/assets/css/material-dashboard.css?v=2.1.0" rel="stylesheet" />
  <!-- CSS Just for demo purpose, don't include it in your project -->
  <link href="<?= ($BASE) ?>/ui/assets/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
  <div class="wrapper ">
    <div class="sidebar" data-color="purple" data-background-color="white" data-image="<?= ($BASE) ?>/ui/assets/img/sidebar-1.jpg">
      <!--
        Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

        Tip 2: you can also add an image using data-image tag
    -->
      <div class="logo">
        <a href="https://github.com/CarlosDevlp/" class="simple-text logo-normal">
          ArduinoVendingMachine <br>El Panel
        </a>
      </div>
      <div class="sidebar-wrapper">
        <ul class="nav">

          <li class="nav-item active ">
            <a class="nav-link" href="./tables.html">
              <i class="material-icons">content_paste</i>
              <p>Clientes</p>
            </a>
          </li>
          <li class="nav-item ">
            <a class="nav-link" href="./tables.html">
              <i class="material-icons">content_paste</i>
              <p>Reporte de ventas</p>
            </a>
          </li>
          
        </ul>
      </div>
    </div>
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
                  <h4 class="card-title ">Lista de usuarios</h4>
                </div>
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table">
                      <thead class=" text-primary">
                        <th>
                          UID de tarjeta RFID
                        </th>
                        <th>
                          Nombre de cliente
                        </th>
                        <th>
                          Saldo disponible
                        </th>
                      </thead>
                      <tbody>
                        <?php foreach (($clientes?:[]) as $cliente): ?>
                            <tr>
                              <td>
                                <?= ($cliente['UID_CARD'])."
" ?>
                              </td>
                              <td>
                                <?= ($cliente['NOMBRE'])."
" ?>
                              </td>
                              <td class="text-primary">
                                S/ <?= ($cliente['SALDO'])."
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
  <!--   Core JS Files   -->
  <script src="<?= ($BASE) ?>/ui/assets/js/core/jquery.min.js" type="text/javascript"></script>
  <script src="<?= ($BASE) ?>/ui/assets/js/core/popper.min.js" type="text/javascript"></script>
  <script src="<?= ($BASE) ?>/ui/assets/js/core/bootstrap-material-design.min.js" type="text/javascript"></script>
  <script src="<?= ($BASE) ?>/ui/assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
  <!--  Google Maps Plugin    -->
  <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
  <!-- Chartist JS -->
  <script src="<?= ($BASE) ?>/ui/assets/js/plugins/chartist.min.js"></script>
  <!--  Notifications Plugin    -->
  <script src="<?= ($BASE) ?>/ui/assets/js/plugins/bootstrap-notify.js"></script>
  <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
  <script src="<?= ($BASE) ?>/ui/assets/js/material-dashboard.min.js?v=2.1.0" type="text/javascript"></script>
  <!-- Material Dashboard DEMO methods, don't include it in your project! -->
  <script src="<?= ($BASE) ?>/ui/assets/demo/demo.js"></script>
</body>

</html>