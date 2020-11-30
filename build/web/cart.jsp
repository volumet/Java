<%-- 
    Document   : cart
    Created on : Oct 11, 2020, 3:06:28 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
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

        <c:set var="cart" value="${sessionScope.CART}"></c:set>
        <c:set var="total" value="${sessionScope.TOTAL}"></c:set>
        <c:set var="quantity_error" value="${requestScope.QUANTITY_ERR}"></c:set>
        <c:forEach var="error" items="${quantity_error.listOfErr}">
            <p class="alert alert-danger">${error}</p>
        </c:forEach>

        <c:choose>
            <c:when test="${not empty cart}">
                <c:set var="compartment" value="${cart.compartment}"></c:set>
                <c:if test="${not empty compartment}">
                    <form action="CheckOut">
                        <c:forEach var="product" items="${compartment}" varStatus="counter">

                            <div class="list-group set-max-width set-left-margin">
                                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h5 class="mb-1">${product.key.name}</h5>
                                    </div>
                                    <input type="number" name="txtQuantity" min="1" value="${product.value}" /> <br/>

                                    ${product.key.price} VND

                                </a>
                                <a href="DeleteProductFromCart?txtProductId=${product.key.productId}" class="set-delete btn btn-primary set-center" 
                                   onclick="return confirm('Do you really want to delete it')">
                                    Delete
                                </a>
                            </div>
                            <br/>
                        </c:forEach>
                        <p class="alert alert-success">Total price is: ${total}</p>

                        <div class="card set-left-margin" style="width: 30rem;">
                            <div class="card-body">
                                <c:if test="${empty account}">
                                    <h5 class="card-title">Please input info:</h5>
                                    <input type="text" name="txtName" value="" placeholder="Name" required class="card-text nav-text" />
                                    <br/>
                                    <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" placeholder="Phone number" class="card-text nav-text" required>
                                    <br/>
                                    <input type="text" name="txtAddress" value="" placeholder="Address" class="card-text nav-text" required />
                                    <br/>
                                </c:if>
                                <input type="submit" value="Check Out" class="btn btn-primary" />
                            </div>
                        </div>



                    </form>
                </c:if>


            </c:when>
            <c:otherwise>
                <p class="set-center" style="font-size:2vw">Your cart is empty!</p>
            </c:otherwise>
        </c:choose>
        <br/>
        <c:set var="tracking" value="${requestScope.TRACKING}"></c:set>
        <c:if test="${not empty tracking}">
            <p class="alert alert-success" style="text-align: center;">
                Your tracking code is:
                <input type="text" value="${tracking}" disabled="" size="70"/>
            </p>

        </c:if>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
