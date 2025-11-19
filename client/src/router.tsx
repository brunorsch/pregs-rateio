import { createBrowserRouter } from "react-router";
import { HomePage, NotFound } from "./presentation/screen";
import type { RouteObject } from "react-router";

type SystemRoutes = "HOME" | "NOT_FOUND";

type Routes = {
  [key in SystemRoutes]: Route;
};

type Route = {
  path: string;
  permissions: string[];
  component: React.ComponentType;
};

export const ROUTES: Routes = {
  HOME: {
    path: "/",
    permissions: [],
    component: HomePage,
  },
  NOT_FOUND: {
    path: "*",
    permissions: [],
    component: NotFound,
  },
};

export const router = createBrowserRouter(
  Object.keys(ROUTES).map((key) => {
    const indiceRota = key as keyof Routes;
    const rota = ROUTES[indiceRota];

    const routeObject: RouteObject = {
      path: rota.path,
      Component: rota.component,
    };

    return routeObject;
  }),
);
