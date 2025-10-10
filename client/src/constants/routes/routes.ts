export const ROUTES: Routes = {
    HOME: {
        path: "/",
        permissions: [] // add user group :)
    },
    NOT_FOUND: {
        path: "*",
        permissions: []
    }
}

type SystemRoutes = "HOME" | "NOT_FOUND"

type Routes = {
    [key in SystemRoutes]: Route
}

type Route = {
    path: string,
    permissions: string[]
}

