<<<<<<< Updated upstream
export default function App() {

  return (
    <>
        <header>
            <div>img</div>
            <div>skanokvnda</div>
        </header>
        <section>
            conteudo
        </section>
        <footer>
            footer
        </footer>
=======
import { useState } from "react";
import viteLogo from "/vite.svg";
import "./App.css";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <Card style={{ width: "18rem" }}>
        <Card.Img variant="top" src="holder.js/100px180" />
        <Card.Body>
          <Card.Title>Card Title</Card.Title>
          <Card.Text>
            Some quick example text to build on the card title and make up the
            bulk of the card's content.
          </Card.Text>
          <Button variant="primary">Go somewhere</Button>
        </Card.Body>
      </Card>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
>>>>>>> Stashed changes
    </>
  );
}
<<<<<<< Updated upstream
=======

export default App;
>>>>>>> Stashed changes
