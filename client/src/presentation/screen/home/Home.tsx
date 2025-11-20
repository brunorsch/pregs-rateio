import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Container from "react-bootstrap/Container";

import "./Home.scss";

export default function HomePage() {
  function Header() {
    return (
      <Container fluid>
        <Navbar className="pregs-nav">
          <Navbar.Brand href=".." className="navbrand">
            Pregs Rateio
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="#features">Features</Nav.Link>
              <Nav.Link href="faq">FAQ</Nav.Link>
              <Nav.Link href="sobre">Sobre</Nav.Link>
              <Nav.Link
                className="planos-link"
                href="#"
                disabled
                title="É de graça xD"
              >
                Planos
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </Container>
    );
  }

  return (
    <>
      <Header />
      <main></main>
      <footer></footer>
    </>
  );
}
