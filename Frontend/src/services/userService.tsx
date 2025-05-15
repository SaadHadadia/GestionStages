import axios from 'axios';
import { getToken } from '../config/Auth';

export interface UserDTO {
  id: number;
  username: string;
  firstname: string;
  lastname: string;
  type: 'Admin' | 'Tuteur' | 'Stagiaire';
  email?: string;
  entreprise?: string;
  institution?: string;
}

export const getUserByUsername = async (encodedUsername: string): Promise<UserDTO> => {
  try {
    const username = atob(encodedUsername); // Decode the base64 username
    const response = await axios.get(
      `${import.meta.env.VITE_API_URL}/api/users/${username}`,
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};