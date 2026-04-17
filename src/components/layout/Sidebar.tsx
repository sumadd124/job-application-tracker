"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { 
  Briefcase, 
  LayoutDashboard, 
  ListTodo, 
  BarChart3, 
  Settings,
  PlusCircle
} from "lucide-react";
import { cn } from "@/lib/utils";

const navItems = [
  { name: "Dashboard", href: "/", icon: LayoutDashboard },
  { name: "Applications", href: "/applications", icon: ListTodo },
  { name: "Analytics", href: "/analytics", icon: BarChart3 },
];

export function Sidebar({ onAddNew }: { onAddNew: () => void }) {
  const pathname = usePathname();

  return (
    <div className="w-64 h-screen border-r border-white/10 glass flex flex-col p-6 fixed left-0 top-0">
      <div className="flex items-center gap-3 mb-10 px-2">
        <div className="p-2 bg-indigo-500/20 rounded-xl">
          <Briefcase className="w-6 h-6 text-indigo-400" />
        </div>
        <span className="text-xl font-bold tracking-tight text-white">JobChase</span>
      </div>

      <nav className="flex-1 space-y-2">
        {navItems.map((item) => (
          <Link
            key={item.name}
            href={item.href}
            className={cn(
              "flex items-center gap-3 px-4 py-3 rounded-xl transition-all duration-200 group text-slate-400 hover:text-white",
              pathname === item.href 
                ? "bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 shadow-[0_0_20px_rgba(99,102,241,0.1)]" 
                : "hover:bg-white/5"
            )}
          >
            <item.icon className={cn(
              "w-5 h-5 transition-transform duration-200 group-hover:scale-110",
              pathname === item.href ? "text-indigo-400" : "text-slate-500 group-hover:text-slate-300"
            )} />
            <span className="font-medium">{item.name}</span>
          </Link>
        ))}
      </nav>

      <div className="mt-auto pt-6 border-t border-white/5">
        <button 
          onClick={onAddNew}
          className="w-full flex items-center gap-3 px-4 py-3 rounded-xl bg-indigo-600 hover:bg-indigo-500 text-white transition-all duration-200 shadow-lg shadow-indigo-600/20 active:scale-95 group"
        >
          <PlusCircle className="w-5 h-5" />
          <span className="font-semibold">Add New</span>
        </button>
        
        <Link
          href="/settings"
          className="flex items-center gap-3 px-4 py-3 rounded-xl mt-4 text-slate-500 hover:text-white transition-colors group"
        >
          <Settings className="w-5 h-5 group-hover:rotate-45 transition-transform duration-500" />
          <span className="font-medium">Settings</span>
        </Link>
      </div>
    </div>
  );
}
