function NotFound() {
  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <div className="flex sm:flex-row flex-col items-center justify-center">
        <h1 className="text-9xl font-bold text-red-500">404</h1>
        <div className="flex flex-col sm:ml-6 sm:items-start justify-center items-center">
          <h1 className="mt-4 flex items-center space-x-2">
            <svg
              aria-hidden="true"
              className="w-6 h-6 text-red-500 dark:text-red-600"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
              />
            </svg>
            <span className="text-xl font-medium text-gray-600 sm:text-2xl dark:text-light">
              Oups! Page non trouvée.
            </span>
          </h1>
          <p>La page que vous recherchez n'existe pas.</p>
          <p>Elle a peut-être été déplacée ou supprimée.</p>
        </div>
      </div>
      <a
        href="/"
        type="button"
        className="mt-4 text-white bg-[#4285F4] hover:bg-[#4285F4]/90 focus:ring-4 focus:outline-none focus:ring-[#4285F4]/50 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#4285F4]/55 me-2 mb-2"
      >
        <svg
          className="w-6 h-4 me-2"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="currentColor"
          viewBox="0 0 18 19"
        >
          <path
            fill-rule="evenodd"
            d="M21 13v10h-6v-6h-6v6h-6v-10h-3l12-12 12 12h-3zm-1-5.907v-5.093h-3v2.093l3 3z"
            clip-rule="evenodd"
          />
        </svg>
        Page d'accueil
      </a>
    </div>
  );
}
export default NotFound;