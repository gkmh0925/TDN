const pageRouter = {
    path: "/",
    name: "layout",
    redirect: "/main",
    component: () => import("@/layout/index.vue"),
    children: [
        {
            path: "/main",
            name: "main",
            component: () => import("@/views/main.vue"),
        },
        {
            path: "/book",
            name:"book",
            component:() => import("@/views/book.vue")
        },
        {
            path: "/classsample",
            name:"classsample",
            component:() => import("@/views/classsample.vue")
        },
        {
            path: "/company",
            name:"company",
            component:() => import("@/views/company.vue")
        },
        {
            path: "/movieclass",
            name:"movieclass",
            component:() => import("@/views/movieclass.vue")
        },
        {
            path: "/reference",
            name:"reference",
            component:() => import("@/views/reference.vue")
        }
    ],
};
export default pageRouter;