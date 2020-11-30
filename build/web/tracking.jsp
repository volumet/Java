<%-- 
    Document   : tracking
    Created on : Oct 12, 2020, 9:02:55 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tracking</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/all.css">
        <link rel="stylesheet" type="text/css" href="css/custom.css">
        <style type="text/css">
            body{
                padding: 0px 0px;
            }
        </style>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.USER}"></c:set>
        <c:set var="role" value="${sessionScope.USER_ROLE}"></c:set>
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand font-color" href="ViewAllProduct">YELLOW MOON</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="ViewAllProduct"><i class="fas fa-home"></i> Home <span class="sr-only">(current)</span></a>
                        </li>

                    <c:choose>
                        <c:when test="${not empty account}">
                            <li class="nav-item">
                                <div class="nav-text">
                                    Hello, <font color="red">${account.name}</font>
                                </div>
                            </li>

                            <li class="nav-item">
                                <a href="Logout" class="nav-link"><i class="fas fa-sign-out-alt"></i> Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a href="Login" class="nav-link">Login</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <li class="nav-item">
                        <c:if test="${role ne 'Admin'}">
                            <a href="ViewCart" class="nav-link"><i class="fas fa-shopping-cart"></i> View Cart</a>
                        </c:if>
                    </li>

                    <li class="nav-item">
                        <c:if test="${role eq 'Admin'}">
                            <form action="Manage">
                                <input type="submit" value="Manage" class="nav-text btn btn-outline-danger set-left-margin" />
                                <input type="hidden" name="txtPage" value="1" />
                            </form>
                        </c:if>
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-outline-primary nav-text set-left-margin" type="button" data-toggle="collapse" data-target="#collapseExample1" aria-expanded="false" aria-controls="collapseExample1">
                            <i class="fas fa-search"></i> Search by Name
                        </button>
                        <div class="collapse" id="collapseExample1">
                            <div class="card card-body">
                                <form action="ViewEachProductServlet" class="form-inline my-2 my-lg-0 set-left-margin">
                                    <input type="text" name="txtSearchValue" value="" class="form-control mr-sm-2" placeholder="Search By Name!" required />
                                    <input type="hidden" name="txtPage" value="1" />
                                    <input type="submit" value="Search" class="btn btn-outline-success my-2 my-sm-0" id="button-addon2" /> 
                                </form>
                            </div>
                        </div>                       
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-outline-primary nav-text set-left-margin" type="button" data-toggle="collapse" data-target="#collapseExample2" aria-expanded="false" aria-controls="collapseExample2">
                            <i class="fas fa-search"></i> Search by Price
                        </button>
                        <div class="collapse" id="collapseExample2">
                            <div class="card card-body">
                                <form action="ViewEachProductServlet" class="form-inline my-2 my-lg-0 set-left-margin">
                                    <input type="number" min="10000" max="500000" step="5000" name="txtLowerPrice" value="" class="form-control mr-sm-2" placeholder="Lower Limit" required />
                                    <input type="number" min="10000" max="500000" step="5000" name="txtUpperPrice" value="" class="form-control mr-sm-2" placeholder="Upper Limit" required />
                                    <input type="hidden" name="txtPage" value="1" />
                                    <input type="submit" value="Search" class="btn btn-outline-success my-2 my-sm-0" id="button-addon2" /> 
                                </form>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item">
                        <c:set var="listCategory" value="${requestScope.LIST_CATEGORY}"></c:set>


                            <button class="btn btn-outline-primary nav-text set-left-margin" type="button" data-toggle="collapse" data-target="#collapseExample3" aria-expanded="false" aria-controls="collapseExample3">
                                <i class="fas fa-search"></i> Search by Category
                            </button>
                            <div class="collapse" id="collapseExample3">
                                <div class="card card-body">
                                    <form action="ViewEachProductServlet" class="form-inline my-2 my-lg-0 set-left-margin">
                                        <select name="cbCategory" class="form-control mr-sm-2">
                                        <c:forEach var="category" items="${listCategory}">
                                            <option value="${category.categoryId}">
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="txtPage" value="1" />
                                    <input type="submit" value="Search" class="btn btn-outline-success my-2 my-sm-0" id="button-addon2" /> 
                                </form>
                            </div>
                        </div>
                    </li>
                    <c:if test="${role eq 'Member'}">
                        <li class="nav-item">
                            <button class="btn btn-outline-primary nav-text set-left-margin" type="button" data-toggle="collapse" data-target="#collapseExample4" aria-expanded="false" aria-controls="collapseExample4">
                                <i class="fas fa-receipt"></i> Tracking order
                            </button>
                            <div class="collapse" id="collapseExample4">
                                <div class="card card-body">
                                    <form action="Tracking" class="form-inline my-2 my-lg-0 set-left-margin">
                                        <input type="text" name="txtTrackingId" value="" placeholder="Tracking your order" required class="form-control mr-sm-2" />
                                        <input type="submit" value="Track" class="btn btn-outline-success my-2 my-sm-0" />
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:if>                  
                </ul>
            </div>              
        </nav>
        <br/>
        <c:set var="order" value="${requestScope.ORDER}"></c:set>
        <c:set var="log_name" value="${requestScope.LOGGED_NAME}"></c:set>
        <c:set var="payment" value="${requestScope.PAYMENT_METHOD}"></c:set>
        <c:set var="order_detail_list" value="${requestScope.ORDER_DETAIL_MAP}"></c:set>

        <c:choose>
            <c:when test="${not empty order}">
                <div class="card border-secondary mb-5 set-center-order">
                    <div class="card-header">
                        <b>YOUR ORDER</b>
                    </div>
                    <div class="card-body text-secondary">
                        <h5 class="card-title">
                            <b>Order ID:</b>
                            ${order.orderId}
                        </h5>
                        <c:choose>
                            <c:when test="${not empty order.name}">
                                <p class="card-text">
                                    <b>Customer: </b>
                                    ${order.name}
                                </p>
                            </c:when>
                            <c:otherwise>
                                <p class="card-text">
                                    <b>Customer:</b>
                                    ${log_name}
                                </p>
                            </c:otherwise>
                        </c:choose>

                        <p class="card-text">
                            <b>Payment method:</b>
                            ${payment}
                        </p>
                        <p class="card-text">
                            <b>Order date: </b>
                            ${order.date}
                        </p>
                        <c:if test="${not empty order.address}">
                            <p class="card-text">
                                <b>Address: </b>
                                ${order.address}
                            </p>
                        </c:if>
                        <c:if test="${not empty order.phone}">
                            <p class="card-text">
                                <b>Phone number: </b>
                                ${order.phone}
                            </p>
                        </c:if>
                    </div>
                </div>


                <div class="set-center-order" style="max-width: 80rem;">
                    <table class="table table-condensed table-hover table-sm table-striped">
                        <caption class="total-font">Total: ${order.total} VND</caption>
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="orderDetail" items="${order_detail_list}" varStatus="counter">
                                <tr class="table-secondary">
                                    <th scope="row">${counter.count}</th>
                                    <td>${orderDetail.value}</td>
                                    <td>${orderDetail.key.quantity}</td>
                                    <td>${orderDetail.key.price} VND</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <p class="alert alert-danger" style="text-align: center">Not found your tracking order!</p>

            </c:otherwise>
        </c:choose>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
