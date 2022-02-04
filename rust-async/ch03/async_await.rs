async fn foo<'a>(x: &'a u8) -> impl Future<Output = u0 + 'a {
    async move { *x }
}
