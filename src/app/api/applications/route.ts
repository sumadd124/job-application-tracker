import { NextResponse } from "next/server";
import prisma from "@/lib/prisma";

export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);
  const query = searchParams.get("q") || "";
  const status = searchParams.get("status") || "";

  try {
    const applications = await prisma.application.findMany({
      where: {
        AND: [
          {
            OR: [
              { company: { contains: query } },
              { role: { contains: query } },
            ],
          },
          status && status !== "All" ? { status } : {},
        ],
      },
      orderBy: { dateApplied: "desc" },
    });
    return NextResponse.json(applications);
  } catch (error) {
    console.error("Error fetching applications:", error);
    return NextResponse.json({ error: "Failed to fetch applications" }, { status: 500 });
  }
}

export async function POST(request: Request) {
  try {
    const body = await request.json();
    const application = await prisma.application.create({
      data: {
        company: body.company,
        role: body.role,
        status: body.status || "Applied",
        dateApplied: body.dateApplied ? new Date(body.dateApplied) : new Date(),
        deadline: body.deadline ? new Date(body.deadline) : null,
        notes: body.notes,
      },
    });
    return NextResponse.json(application);
  } catch (error) {
    console.error("Error creating application:", error);
    return NextResponse.json({ error: "Failed to create application" }, { status: 500 });
  }
}
