const Home = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-blue-50">
      <h1 className="text-4xl font-bold mb-4">Welcome to the App</h1>
      <p className="text-gray-700">Please login or register to continue.</p>
      <div className="mt-6">
        <a
          href="/login"
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Login
        </a>
        <a
          href="/register"
          className="ml-4 bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
        >
          Register
        </a>
      </div>
    </div>
  );
};

export default Home;
