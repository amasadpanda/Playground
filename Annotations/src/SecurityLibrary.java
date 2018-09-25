interface SecurityLibrary {
    <T> Class<? extends T> secure(Class<T> instance);
}
