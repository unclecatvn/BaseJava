<%-- 
    Document   : login
    Created on : May 12, 2024, 5:38:15 PM
    Author     : Hoai Nam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="csrf-param" content="_csrf">
        <title>Đăng nhập</title>
        <link rel="shortcut icon" href="">
    </head>

    <body>
        <div class="wrap">
            <div class="login-wrapper">
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-md-offset-4 col-sm-offset-3 col-sm-6 col-xs-offset-1 col-xs-10">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="text-center" style="margin: 0;text-transform: uppercase">Đăng nhập hệ thống
                                    </h4>
                                </div>
                                <div class="panel-body">
                                    <div class="site-login">
                                        <div class="row">
<!--                                            <img src="{{ asset('images/logo.jpg') }}" alt="" class="img-responsive"
                                                 style="width: 250px;margin: 0 auto">-->
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <form id="frm_login" action="auth"
                                                      method="POST" role="form">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <i class="fa fa-user" aria-hidden="true"></i>
                                                            <div class="form-group required ">
                                                                <input type="username" id="username" class="form-control"
                                                                       name="username" placeholder="Tên đăng nhập">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <i class="fa fa-lock"></i>
                                                            <input type="password" id="password" name="password"
                                                                   placeholder="Mật khẩu">
<!--                                                            @if ($message !== '')
                                                            <label for="password" generated="true"
                                                                   class="error">{{ $message }}</label>
                                                            @endif-->
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                ${message}
                                <div class="panel-footer">
                                    <div class="row wrap-row-login">
                                        <button type="text" id="btn_submit" class="btn btn-primary btn-lg btn-block">
                                            Đăng nhập
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
