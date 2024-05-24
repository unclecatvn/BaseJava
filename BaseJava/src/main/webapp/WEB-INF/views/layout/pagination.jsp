<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="container" style="display:flex">
    <input type="hidden" name="_currentPage" id="_currentPage" value="${currentPage}">
    <div class="col-sm-3" style="margin-top: -5px">
        <div class="dataTables_info" style="margin-top: 5px;"><span class="page-link">Danh sách có
                ${currentPage} trong tổng số ${pages} trang</span></div>
    </div>
    <div class="col-sm-6" style="display: flex; justify-content: center;">
        <div class="main_paginate">
            <c:if test="${pages > 1}">
                <ul class="pagination pagination-sm" style="margin: 0;white-space: nowrap;text-align: center;">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item"><a href="?page=${currentPage - 1}&pageSize=${pageSize}" class="page-link" rel="prev">Trước</a></li>
                        </c:if>
                        <c:if test="${currentPage == 1}">
                        <li class="page-item disabled"><span class="page-link">Trước</span></li>
                        </c:if>

                    <c:forEach begin="1" end="${pages}" var="page">
                        <c:choose>
                            <c:when test="${page == currentPage}">
                                <li class="page-item active"><span class="page-link">${page}</span></li>
                                </c:when>
                                <c:otherwise>
                                <li class="page-item"><a href="?page=${page}&pageSize=${pageSize}" class="page-link">${page}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                    <c:if test="${currentPage < pages}">
                        <li class="page-item"><a href="?page=${currentPage + 1}&pageSize=${pageSize}" class="page-link" rel="next">Tiếp</a></li>
                        </c:if>
                        <c:if test="${currentPage >= pages}">
                        <li class="page-item disabled"><span class="page-link">Tiếp</span></li>
                        </c:if>
                </ul>
            </c:if>
        </div>
    </div>
    <div class="col-sm-3" style="padding-bottom: 5px;">
        <div class="left_paginate" style="display:flex; align-items: center">
            <div class="col-sm-6">
                <span class="page-link">Hiển thị</span>
            </div>
            <div class="col-sm-6">
                <select style="font-size:14px; max-width: 100%;" id="pageSize" class="col-sm-1 form-control input-sm" name="pageSize" onchange="window.location.href = '?page=1&pageSize=' + this.value">
                    <option value="15" ${pageSize == 15 ? 'selected' : ''}>10</option>
                    <option value="30" ${pageSize == 30 ? 'selected' : ''}>30</option>
                    <option value="100" ${pageSize == 100 ? 'selected' : ''}>100</option>
                </select>
            </div>
        </div>
    </div>
</div>