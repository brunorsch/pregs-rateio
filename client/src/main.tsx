<<<<<<< Updated upstream
import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {RouterProvider} from "react-router/dom";
import {router} from "./router.tsx";

import './index.css'

const root = document.getElementById("root");

createRoot(root!).render(
    <StrictMode>
        <RouterProvider router={router}/>
    </StrictMode>
);
=======
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "bootswatch/dist/litera/bootstrap.min.css"; // Added this :boom:
import "./index.css";
import App from "./App.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App />
  </StrictMode>,
);
>>>>>>> Stashed changes
