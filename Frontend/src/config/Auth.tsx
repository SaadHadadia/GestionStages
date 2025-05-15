export const saveToken = (token: string) => {
  localStorage.setItem("token", token);
};

export const getToken = (): string | null => {
  return localStorage.getItem("token");
};

export const removeToken = () => {
  localStorage.removeItem("token");
};

export const isAuthenticated = (): boolean => {
  return !!localStorage.getItem('userData');
};

export type UserData = {
  username: string;
  type: 'Admin' | 'Tuteur' | 'Stagiaire';
  token: string;
};

export const saveUserData = (data: UserData) => {
  localStorage.setItem('userData', JSON.stringify(data));
  // Also save token separately for backward compatibility
  localStorage.setItem('token', data.token);
};

export const getUserData = (): UserData | null => {
  const data = localStorage.getItem('userData');
  return data ? JSON.parse(data) : null;
};

export const logout = () => {
  localStorage.removeItem('userData');
  localStorage.removeItem('token');
};
