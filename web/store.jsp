<%-- 
    Document   : store
    Created on : Oct 7, 2020, 9:43:39 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store</title>
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
        <c:set var="quantity_error" value="${requestScope.QUANTITY_ERR}"></c:set>
        <c:forEach var="error" items="${quantity_error.listOfErr}">
            <font color="red">${error}</font> <br/>
        </c:forEach>

        <br/>
        <c:set var="listOfProducts" value="${requestScope.LIST_PRODUCTS}"></c:set>
        <c:if test="${not empty listOfProducts}">
            <div class="container">
                <div class="row">
                    <c:forEach var="product" items="${listOfProducts}" varStatus="counter">
                        <div class="card col-lg-3 col-sm-6 set-margin" >                
                            <c:if test="${not empty product.image}">
                                <img class="card-img-top" src="data:image/png;base64,${product.image}" alt="Card image cap">
                            </c:if>
                            <div class="card-body">
                                <h5 class="card-title">${product.name}</h5>
                                <p class="card-text">${product.price} VND</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <c:choose>
                                    <c:when test="${product.quantity gt 0}">
                                        <li class="list-group-item">
                                            Quantity: ${product.quantity}
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="list-group-item">
                                            <font color="red">
                                            Out of stock
                                            </font>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <li class="list-group-item">
                                    Created date: 
                                    <input type="date" name="" value="${product.createDate}" disabled="" />
                                </li>
                                <li class="list-group-item">
                                    Expired date:
                                    <input type="date" name="" value="${product.expiredDate}" disabled="" />
                                </li>
                            </ul>
                            <c:if test="${role ne 'Admin'}">
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${product.quantity gt 0}">
                                            <form action="AddToCart">
                                                <input type="hidden" name="txtProductId" value="${product.productId}" />
                                                <input type="submit" value="Add to Cart" class="nav-text btn btn-outline-danger set-left-margin" />
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="submit" value="Add to Cart" class="nav-text btn btn-outline-danger set-left-margin" disabled=""/>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <br/>
        <a href="ViewFull?txtSearchValue=&txtPage=1" class="btn btn-primary set-center">View full</a>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
