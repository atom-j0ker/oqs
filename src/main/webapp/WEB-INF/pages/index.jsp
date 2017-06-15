<%@ page import="com.oqs.crud.UserDAO" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Online Queue System</title>
    <style>
        <%@ include file="/resources/vendor/bootstrap/css/bootstrap.min.css" %>
        <%@ include file="/resources/vendor/font-awesome/css/font-awesome.min.css" %>
        <%@ include file="/resources/css/agency.min.css" %>
        <%@ include file="/resources/css/agency.css" %>
        <c:import url="https://fonts.googleapis.com/css?family=Montserrat:400,700"/>
        <c:import url="https://fonts.googleapis.com/css?family=Kaushan+Script"/>
        <c:import url="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic"/>
        <c:import url="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"/>
    </style>


    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

    <!-- jQuery -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="/resources/js/jqBootstrapValidation.js"></script>
    <script src="/resources/js/contactUs.js"></script>

    <!-- Theme JavaScript -->
    <script src="/resources/js/agency.min.js"></script>

</head>
<body id="page-top" class="index">

<%
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDAO userDAO = WebApplicationContextUtils.getWebApplicationContext(application).getBean(UserDAO.class);
    String username;
    long userId = 0;

    if (principal instanceof UserDetails) {
        username = ((UserDetails) principal).getUsername();
    } else {
        username = principal.toString();
    }

    if (!username.equals("anonymousUser"))
        userId = userDAO.getUserIdByUsername(username);
    session.setAttribute("userId", userId);
%>

<jsp:include page="fragments/header.jsp"/>

<header>
    <div class="container">
        <div class="intro-text">
            <div class="intro-lead-in">Welcome To Online Queue System!</div>
            <div class="intro-heading">It's Nice To Meet You</div>
            <a href="/organizations" class="page-scroll btn btn-xl">List of organizations</a>
        </div>
    </div>
</header>

<!-- Services Section -->
<section id="services">
    <h2 class="section-heading">Organization types</h2>
    <div class="container">
        <form class="organization-types" action="/organizations-sort-by" method="post">
            <input type="hidden" value="2" name="typeId"/>
            <input class="organization-types-image" type="image"
                   src="/resources/img/organizations-logo/beauty-salon.png"/>
            <p class="organization-types-description"><strong>BEAUTY SALONS</strong><br>
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

        <form class="organization-types" action="/organizations-sort-by" method="post">
            <input type="hidden" value="3" name="typeId"/>
            <input class="organization-types-image" style="float: right" type="image"
                   src="/resources/img/organizations-logo/service-station.png"/>
            <p class="organization-types-description"><strong>SERVICE STATIONS</strong><br>
                A service station  is a repair shop where automobiles are repaired by auto mechanics
                and technicians. Specialty automobile repair shops are shops specializing in certain
                parts such as brakes, mufflers and exhaust systems, transmissions, body parts,
                automobile electrification, automotive air conditioner repairs, automotive glass
                repairs and installation, and wheel alignment or those who only work on certain
                brands of vehicle or vehicles from certain continents of the world. <br>There are also
                automotive repair shops that specialize in vehicle modifications and customization.
            </p>
        </form>

        <form class="organization-types" action="/organizations-sort-by" method="post">
            <input type="hidden" value="4" name="typeId"/>
            <input class="organization-types-image" type="image"
                   src="/resources/img/organizations-logo/medicine-center.png"/>
            <p class="organization-types-description"><strong>MEDICAL CENTERS</strong><br>
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

<!-- About Section -->
<section id="about">
    <h2 class="section-heading">About our organizations</h2>
    <div class="container">
        <%--<div class="row">--%>
            <%--<div class="col-lg-12 text-center">--%>
                <%----%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="row">
            <div class="col-lg-12">
                <ul class="timeline">
                    <li>
                        <div class="timeline-image">
                            <img class="img-circle img-responsive" src="/resources/img/about/zigzag-about.jpg" alt="">
                        </div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4 class="subheading">ZIG-ZAG</h4>
                            </div>
                            <div class="timeline-body">
                                <p class="text-muted">Beauty is a mosaic, a collection of the smallest details. Watching your hair, skin and
                                    nails is a very important moment in caring for your appearance. ZIG-ZAG is a special
                                    beauty salon that specializes in the spectrum of services related to the modeling of
                                    eyebrows and eyelashes, makeup, hairdressing and manicure - the main components of a
                                    beautiful and literate image! <br>Now ZIG-ZAG and Beauty are synonymous!
                                </p>
                            </div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image">
                            <img class="img-circle img-responsive" src="/resources/img/about/acdelco-about.jpg" alt="">
                        </div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4 class="subheading">ACDelco</h4>
                            </div>
                            <div class="timeline-body">
                                <p class="text-muted">All components, parts, related products and auto repair are territorially uniform.
                                    Also we closely follow the constant availability of our range. <br>We have assembled
                                    the best team of masters, continuing to develop and provide them with everything
                                    necessary! Every master in our company owns not only experience, but also is
                                    additionally trained by seminars, trainings and courses from leading international
                                    companies. Masters ACDelco specialize in all brands of cars.</p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="timeline-image">
                            <img class="img-circle img-responsive" src="/resources/img/about/ouphysicians-about.jpg" alt="">
                        </div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4 class="subheading">OU Physicians</h4>
                            </div>
                            <div class="timeline-body">
                                <p class="text-muted">At OU Medicine, our mission is leading health care. Our vision is
                                    to be the premiere enterprise for advancing health care, medical education and
                                    research for the community, state and region. Through our combined efforts we strive
                                    to improve the lives of all people.</p>
                            </div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image">
                            <img class="img-circle img-responsive" src="/resources/img/about/evolve-about.jpg" alt="">
                        </div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4 class="subheading">Evolve</h4>
                            </div>
                            <div class="timeline-body">
                                <p class="text-muted">If you are used to looking good, if you like a well-groomed appearance, then you have
                                    found what you were looking for. Beauty Salon Evolve offers a full range of services
                                    for men, women and children. Evolve is a salon for the whole family.
                                    <br>The specialists of our salon will do everything to make you satisfied with the
                                    result and quality of the work was done. After visiting with us, you will understand
                                    that BEAUTY is very close.
                                </p>
                            </div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image">
                            <h4>Be Part
                                <br>Of Our
                                <br>Story!</h4>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</section>

<!-- Contact Section -->
<section id="contact">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading" style="padding-top: 50px">Contact Us</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <form name="sentMessage" id="contactForm" novalidate>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Your Name *" id="name" name="name"
                                       required
                                       data-validation-required-message="Please enter your name.">
                                <p class="help-block text-danger"></p>
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Your Email *" id="email"
                                       name="email" required
                                       data-validation-required-message="Please enter your email address.">
                                <p class="help-block text-danger"></p>
                            </div>
                            <div class="form-group">
                                <input type="tel" class="form-control" placeholder="Your Phone *" id="phone"
                                       name="phone" required
                                       data-validation-required-message="Please enter your phone number.">
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <textarea class="form-control" placeholder="Your Message *" id="message" name="message"
                                          required
                                          data-validation-required-message="Please enter a message."></textarea>
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-lg-12 text-center">
                            <div id="success"></div>
                            <button type="submit" id="send-msg-btn" class="btn btn-xl">Send Message</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <span class="copyright">Copyright &copy; Online queue system 2017</span>
            </div>
            <div class="col-md-4">
                <ul class="list-inline social-buttons">
                    <li><a href="https://twitter.com" target="_blank"><i class="fa fa-twitter"></i></a>
                    </li>
                    <li><a href="https://www.facebook.com" target="_blank"><i class="fa fa-facebook"></i></a>
                    </li>
                    <li><a href="https://www.linkedin.com" target="_blank"><i class="fa fa-linkedin"></i></a>
                    </li>
                </ul>
            </div>
            <div class="col-md-4">
                <c:set var="date" value="<%=new java.util.Date()%>"/>
                <p><fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/></p>
            </div>
        </div>
    </div>
</footer>

</body>

</html>
