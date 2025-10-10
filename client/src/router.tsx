import {createBrowserRouter} from "react-router";
import {HomePage, NotFound} from "./application";
import {ROUTES} from "./constants";

export const router = createBrowserRouter([
    {
        path: ROUTES.HOME.path,
        Component: HomePage
    },
    {
        path: ROUTES.NOT_FOUND.path,
        Component: NotFound
    }
]);
