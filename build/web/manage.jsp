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

        <c:set var="create_error" value="${requestScope.CREATE_ERROR}"></c:set>
        <c:if test="${not empty create_error.createExpiredErr}">
            <p class="alert alert-danger set-text-center">${create_error.createExpiredErr}</p>
        </c:if>
        <c:if test="${not empty create_error.duplicateIdErr}">
            <p class="alert alert-danger set-text-center">${create_error.duplicateIdErr}</p>
        </c:if>
        <form action="CreateNewProduct" method="POST" enctype="multipart/form-data" class="form-inline my-2 my-lg-0 set-left-margin">
            <div class="container">
                <div class="card col-lg-6 col-sm-6 set-margin" >                
                    <div class="card-body">
                        <h5 class="card-title">Create new product:</h5>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            Product Id: 
                            <input type="text" name="txtProductId" value="" placeholder="Product Id" class="form-control mr-sm-2" required />
                        </li>
                        <li class="list-group-item">
                            Name: 
                            <input type="text" name="txtName" required class="form-control mr-sm-2" placeholder="Product Name" />
                        </li>
                        <li class="list-group-item">
                            Price:
                            <input type="number" name="txtPrice" step="5000" min="10000" required class="form-control mr-sm-2" placeholder="Price" />
                        </li>
                        <li class="list-group-item">
                            Quantity:
                            <input type="number" name="txtQuantity" min="0"  required placeholder="Quantity" class="form-control mr-sm-2" />
                        </li>
                        <li class="list-group-item">
                            Create date:
                            <input type="date" name="txtCreateDate" value="${product.createDate}" required placeholder="Create Date" class="form-control mr-sm-2" />
                        </li>
                        <li class="list-group-item">
                            Expired date:
                            <input type="date" name="txtExpiredDate" value="${product.expiredDate}" required placeholder="Expired Date" class="form-control mr-sm-2" />
                        </li>
                        <li class="list-group-item">
                            Category:
                            <c:set var="categoryList" value="${requestScope.LIST_CATEGORY}"></c:set>
                                <select name="cbCategory" class="form-control mr-sm-2">
                                <c:forEach var="category" items="${categoryList}" varStatus="counter">
                                    <option value="${category.categoryId}">
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </li>
                        <li class="list-group-item">
                            <input type="file" name="photo" accept="image/*" class="form-control mr-sm-2" required="" />
                        </li>
                    </ul>
                    <input type="hidden" name="txtStatus" value="1" />    
                    <input type="hidden" name="txtPage" value="${param.txtPage}" />
                    <input type="submit" value="Create" class="btn btn-outline-success my-2 my-sm-0 set-center" />            
                </div>
            </div>
        </form>
        <br/>


        <c:set var="update_error" value="${requestScope.UPDATE_ERROR}"></c:set>
        <c:if test="${not empty update_error.createExpiredErr}">
            <p class="alert alert-danger set-text-center">${update_error.createExpiredErr}</p>
        </c:if>
            
            
        <c:set var="listOfProducts" value="${requestScope.LIST_PRODUCTS}"></c:set>
        <c:if test="${not empty listOfProducts}">
            <p class="alert alert-success set-text-center">UPDATE PRODUCTS</p>
            <c:forEach var="product" items="${listOfProducts}" varStatus="counter">
                <div class="update-padding margin-left">
                    <form action="Update" method="POST" enctype="multipart/form-data" class="form-inline my-2 my-lg-0 set-left-margin">
                        <input type="text" name="txtName" value="${product.name}" required class="form-control mr-sm-2" />
                        <input type="number" name="txtPrice" step="5000" min="10000" value="${product.price}" required class="form-control mr-sm-2" />
                        <input type="number" name="txtQuantity" min="0" value="${product.quantity}" required class="form-control mr-sm-2" />
                        <input type="date" name="txtCreateDate" value="${product.createDate}" required class="form-control mr-sm-2" />
                        <input type="date" name="txtExpiredDate" value="${product.expiredDate}" required class="form-control mr-sm-2" />
                        <c:set var="categoryList" value="${requestScope.LIST_CATEGORY}"></c:set>
                            <select name="cbCategory" class="form-control mr-sm-2">
                            <c:forEach var="category" items="${categoryList}" varStatus="counter">
                                <option value="${category.categoryId}"
                                        <c:if test="${product.categoryId eq category.categoryId}">
                                            selected="selected"    
                                        </c:if>
                                        >
                                    ${category.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                        <c:set var="statusList" value="${requestScope.LIST_STATUS}"></c:set>
                            <select name="cbStatus" class="form-control mr-sm-2">
                            <c:forEach var="status" items="${statusList}" varStatus="counter">
                                <option value="${status.statusId}"
                                        <c:if test="${product.statusId eq status.statusId}">
                                            selected="selected"    
                                        </c:if>
                                        >
                                    ${status.statusName}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty product.image}">
                            <img src="data:image/png;base64,${product.image}" width="100" length="100""/>
                        </c:if>

                        <input type="file" name="photo" accept="image/*" class="form-control mr-sm-2"/>

                        <input type="hidden" name="txtProductId" value="${product.productId}" />
                        <input type="hidden" name="txtPage" value="${param.txtPage}" />
                        <input type="hidden" name="txtUserId" value="${account.userId}" />
                        <input type="submit" value="Update" class="btn btn-outline-success my-2 my-sm-0" />
                    </form>
                    <br/>
                </div>
            </c:forEach>

            <c:set var="paging" value="${requestScope.PAGING}"></c:set>

                <nav aria-label="Page navigation" class="set-left-margin">
                    <ul class="pagination">
                    <c:if test="${param.txtPage ne 1}">
                        <li class="page-item">
                            <a class="page-link" href="Manage?txtPage=${param.txtPage - 1}">
                                Previous
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${param.txtPage eq 1}">
                        <li class="page-item">
                            <a class="page-link disabled" href="manage.jsp">
                                Previous
                            </a>
                        </li>
                    </c:if>    


                    <c:forEach var="thisPage" begin="1" end="${paging}" varStatus="counter">
                        <li class="page-item">
                            <c:if test="${param.txtPage ne counter.count}">
                                <a class="page-link" href="Manage?txtPage=${counter.count}">
                                    ${counter.count}
                                </a>
                            </c:if>
                            <c:if test="${param.txtPage eq counter.count}">
                                <a href="manage.jsp" class="page-link disabled">${counter.count}</a>
                            </c:if>
                        </li>
                    </c:forEach>


                    <c:if test="${param.txtPage ne paging}">
                        <li class="page-item">
                            <a class="page-link" href="Manage?&txtPage=${param.txtPage + 1}">
                                Next
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${param.txtPage eq paging}">
                        <li class="page-item">
                            <a class="page-link disabled" href="manage.jsp">
                                Next
                            </a>
                        </li>
                    </c:if> 
                </ul>
            </nav>    


        </c:if>
            <a href="ShowAccount">Click me</a>
        <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
