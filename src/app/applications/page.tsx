"use client";

import { useEffect, useState } from "react";
import { AppLayout } from "@/components/layout/AppLayout";
import { Search, Filter, MoreVertical } from "lucide-react";
import { cn, formatDate } from "@/lib/utils";

interface Application {
  id: number;
  company: string;
  role: string;
  status: string;
  dateApplied: string;
}

export default function ApplicationsPage() {
  const [apps, setApps] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("/api/applications")
      .then(res => res.json())
      .then(data => {
        setApps(data);
        setLoading(false);
      });
  }, []);

  return (
    <AppLayout>
      <header className="flex flex-col md:flex-row md:items-end justify-between gap-6 mb-10">
        <div>
          <h1 className="text-4xl font-extrabold tracking-tight text-white mb-2">My Applications</h1>
          <p className="text-slate-400">Keep track of every opportunity in your pipeline.</p>
        </div>
        
        <div className="flex items-center gap-3">
          <div className="relative group">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-500 group-focus-within:text-indigo-400 transition-colors" />
            <input 
              type="text" 
              placeholder="Search companies..."
              className="pl-11 pr-4 py-3 bg-white/5 border border-white/10 rounded-2xl text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500/50 w-64 transition-all"
            />
          </div>
          <button className="p-3 bg-white/5 border border-white/10 rounded-2xl text-slate-400 hover:text-white hover:bg-white/10 transition-all">
            <Filter className="w-5 h-5" />
          </button>
        </div>
      </header>

      <div className="glass-card rounded-[2rem] overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-white/5 text-slate-400 text-sm uppercase tracking-wider font-semibold">
                <th className="px-8 py-6">Company</th>
                <th className="px-8 py-6">Position</th>
                <th className="px-8 py-6">Status</th>
                <th className="px-8 py-6">Applied date</th>
                <th className="px-8 py-6"></th>
              </tr>
            </thead>
            <tbody className="divide-y divide-white/5">
              {loading ? (
                [...Array(5)].map((_, i) => (
                  <tr key={i} className="animate-pulse">
                    <td className="px-8 py-6"><div className="h-5 bg-white/5 rounded w-32" /></td>
                    <td className="px-8 py-6"><div className="h-5 bg-white/5 rounded w-48" /></td>
                    <td className="px-8 py-6"><div className="h-5 bg-white/5 rounded w-20" /></td>
                    <td className="px-8 py-6"><div className="h-5 bg-white/5 rounded w-24" /></td>
                    <td className="px-8 py-6" />
                  </tr>
                ))
              ) : apps.length === 0 ? (
                <tr>
                  <td colSpan={5} className="px-8 py-20 text-center text-slate-500">
                    No applications found. Start by adding one!
                  </td>
                </tr>
              ) : (
                apps.map((app) => (
                  <tr key={app.id} className="hover:bg-white/[0.02] transition-colors group">
                    <td className="px-8 py-6">
                      <div className="flex items-center gap-3">
                        <div className="w-10 h-10 rounded-xl bg-indigo-500/10 flex items-center justify-center text-indigo-400 font-bold">
                          {app.company[0]}
                        </div>
                        <span className="font-semibold text-white">{app.company}</span>
                      </div>
                    </td>
                    <td className="px-8 py-6 text-slate-300 font-medium">{app.role}</td>
                    <td className="px-8 py-6">
                      <span className={cn(
                        "px-3 py-1 rounded-full text-[10px] font-black uppercase tracking-widest",
                        app.status === "Interview" && "bg-amber-400/10 text-amber-500",
                        app.status === "Applied" && "bg-blue-400/10 text-blue-500",
                        app.status === "Offer" && "bg-emerald-400/10 text-emerald-500",
                        app.status === "Rejected" && "bg-rose-400/10 text-rose-500"
                      )}>
                        {app.status}
                      </span>
                    </td>
                    <td className="px-8 py-6 text-slate-400 text-sm">{formatDate(app.dateApplied)}</td>
                    <td className="px-8 py-6 text-right">
                      <button className="p-2 text-slate-600 hover:text-white transition-colors">
                        <MoreVertical className="w-5 h-5" />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </AppLayout>
  );
}
