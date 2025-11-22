import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import Logo from "../../assets/logo/pregs-logo.png";
import "./Home.scss";
import { Card, type TCard } from "../../components/card";

const cards: TCard[] = [
  {
    title: "100% gratuito",
    description:
      "Nosso serviço é totalmente gratuito, sem taxas ou cobranças embutidas.",
    icon: "money",
  },
  {
    title: "Fácil de usar",
    description:
      "Nossa interface intuitiva permite a qualquer pessoa entender rapidamente o funcionamento da plataforma.",
    icon: "gear",
  },
  {
    title: "Compartilhe",
    description:
      "Compartilhe as depesas automaticamente com um link unico para seus amigos.",
    icon: "bookmark",
  },
];

export default function HomePage() {
  function Header() {
    return (
      <Container fluid className="container-nav">
        <Navbar expand="lg" className="pregs-nav">
          <Navbar.Brand href="/" className="navbrand">
            Pregs Rateio
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="ms-auto">
              <Nav.Link href="#features">Features</Nav.Link>
              <Nav.Link href="/faq">FAQ</Nav.Link>
              <Nav.Link href="/sobre">Sobre</Nav.Link>
              <Nav.Link disabled className="planos-link" title="É de graça xD">
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
      <div className="curva-pregs"></div>

      <main>
        <Container className="container-info" fluid>
          <img alt="logo" width={160} height={160} src={Logo} />

          <h1 className="titulo">
            O jeito mais fácil <br />
            <span>de rachar a conta!</span>
          </h1>

          <p className="sub">
            <b> Divida despesas com amigos sem se preocupar em </b> gerenciar
            tudo manualmente. Sem taxas e sem cobrança!
          </p>

          <div className="btns-pregs">
            <Button className="btn-primary-custom mt-3">
              Criar meu primeiro rateio!
            </Button>

            <Button variant="outline-secondary" className="btn-faq mt-3">
              Leia nosso FAQ
            </Button>
          </div>
        </Container>

        <Container className="features mt-5">
          <Row className="justify-content-center g-4">
            {cards.map((card) => (
              <Col md={3} className="d-flex justify-content-center mb-3">
                <Card {...card} />
              </Col>
            ))}
          </Row>
        </Container>
      </main>
      <footer></footer>
    </>
  );
}
