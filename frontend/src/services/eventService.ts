const API_URL = import.meta.env.VITE_API_URL as string;

import { createErrorResponse, createSuccessResponse } from "./utils";

import DateFormatter from "../utils/DateFormatter";

const getEvents = async (page: number = 1, size: number = 10) => {
  try {
    const response = await fetch(
      `${API_URL}/user/events/all?page=${page}&size=${size}`,
      {
        method: "GET",
        credentials: "include",
      }
    );
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message);
    }
    return {
      status: "ok",
      message: "Pobrano wydarzenia.",
      events: data.events,
      eventsNumber: data.eventsNumber,
    };
  } catch (error) {
    return createErrorResponse(error);
  }
};

const getUpcomingEvents = async () => {
  try {
    const response = await fetch(`${API_URL}/user/events/all?page=1&size=3`, {
      method: "GET",
      credentials: "include",
    });
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message);
    }
    return {
      status: "ok",
      message: "Pobrano wydarzenia.",
      events: data.events,
      eventsNumber: data.eventsNumber,
    };
  } catch (error) {
    return createErrorResponse(error);
  }
};

const getEventById = async (id: number) => {
  try {
    const response = await fetch(`${API_URL}/user/events?eventId=${id}`, {
      method: "GET",
      credentials: "include",
    });
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message);
    }
    return {
      status: "ok",
      message: "Pobrano wydarzenie.",
      event: data,
    };
  } catch (error) {
    return createErrorResponse(error);
  }
};

const createEvent = async (
  title: string,
  city: string,
  date: Date | null | [Date | null, Date | null],
  description: string,
  image: File
) => {
  const formatDate = DateFormatter.toISOStringWithTimezoneOffset(
    new Date(date as Date)
  );

  const formData = new FormData();
  formData.append("title", title);
  formData.append("city", city);
  formData.append("eventDate", formatDate);
  formData.append("description", description);
  formData.append("image", image);
  try {
    const response = await fetch(`${API_URL}/user/events`, {
      method: "POST",
      credentials: "include",
      body: formData,
    });
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message);
    }
    return {
      status: "ok",
      message: "Dodano wydarzenie.",
      event: data,
    };
  } catch (error) {
    return createErrorResponse(error);
  }
};

const deleteEvent = async (id: number) => {
  try {
    const response = await fetch(`${API_URL}/user/events?eventId=${id}`, {
      method: "DELETE",
      credentials: "include",
    });
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message);
    }
    return createSuccessResponse("Usunięto wydarzenie.");
  } catch (error) {
    return createErrorResponse(error);
  }
};

export { getEvents, getEventById, createEvent, deleteEvent, getUpcomingEvents };
