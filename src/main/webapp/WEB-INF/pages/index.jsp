<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>online queue system</title>
    <style>
        <%@ include file="/resources/css/bootstrap.min.css" %>
        <%@ include file="/resources/css/oqs.css" %>
    </style>

</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<header>
    <div class="container">
        <div class="intro-text">
            <div class="intro-lead-in">Welcome<br>To:<br><br>Online<br>Queue<br>System</div>
            <div class="intro-heading">It's Nice To Meet You</div>
            <p class="intro-advice">Save Your Time
                <a href="/organizations" class="btn btn-xl">Organizations</a>
            </p>
        </div>
    </div>
</header>

<section id="services">
    <h2 class="section-heading">Organization Categories</h2>
    <div class="container">
        <form class="organization-types" action="/organizationsSortByCategory" method="post">
            <input type="hidden" value="2" name="categoryId"/>
            <input type="hidden" value="Beauty" name="categoryName"/>
            <input class="organization-types-image" type="image"
                   src="/resources/photos/categories/beauty-salon.png"/>
            <p class="organization-types-description"><strong>BEAUTY</strong><br>
                A beauty salon is an establishment dealing with cosmetic treatments for men and women.
                Other variations of this type of business include hair salons and spas.
                <br>There is a distinction between a beauty salon and a hair salon and although many
                small businesses do offer both sets of treatments; beauty salons provide extended
                services related to skin health, facial aesthetic, foot care, nail manicures,
                aromatherapy, even meditation, oxygen therapy, mud baths, and many other services.
                <br>Specialized beauty salons known as nail salons offer treatments such as manicures
                and pedicures for the nails.
            </p>
        </form>

        <form class="organization-types" action="/organizationsSortByCategory" method="post">
            <input type="hidden" value="3" name="categoryId"/>
            <input type="hidden" value="Service" name="categoryName"/>
            <input class="organization-types-image" style="float: right" type="image"
                   src="/resources/photos/categories/service-station.png"/>
            <p class="organization-types-description"><strong>SERVICE</strong><br>
                A service station is a repair shop where automobiles are repaired by auto mechanics
                and technicians. Specialty automobile repair shops are shops specializing in certain
                parts such as brakes, mufflers and exhaust systems, transmissions, body parts,
                automobile electrification, automotive air conditioner repairs, automotive glass
                repairs and installation, and wheel alignment or those who only work on certain
                brands of vehicle or vehicles from certain continents of the world. <br>There are also
                automotive repair shops that specialize in vehicle modifications and customization.
            </p>
        </form>

        <form class="organization-types" action="/organizationsSortByCategory" method="post">
            <input type="hidden" value="1" name="categoryId"/>
            <input type="hidden" value="Medicine" name="categoryName"/>
            <input class="organization-types-image" type="image"
                   src="/resources/photos/categories/medicine-center.png"/>
            <p class="organization-types-description"><strong>MEDICINE</strong><br>
                A medical center or a clinic is a healthcare facility that is primarily focused on
                the care of outpatients. Clinics can be privately operated or publicly managed and funded.
                They typically cover the primary healthcare needs of populations in local communities,
                in contrast to larger hospitals which offer specialised treatments and admit inpatients
                for overnight stays. <br>Clinics are often associated with a general medical practice run
                by one or several general practitioners. Other types of clinics are run by the type of
                specialist associated with that type: physical therapy clinics by physiotherapists and
                psychology clinics by clinical psychologists, and so on for each health profession.
            </p>
        </form>

    </div>
</section>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
