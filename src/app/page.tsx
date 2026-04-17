import { AppLayout } from "@/components/layout/AppLayout";
import { 
  Users, 
  Send, 
  CheckCircle2, 
  XCircle,
  TrendingDown
} from "lucide-react";
import { cn } from "@/lib/utils";

const stats = [
  { name: "Total Applications", value: "24", icon: Send, color: "text-blue-400", bg: "bg-blue-400/10" },
  { name: "Interviews", value: "5", icon: Users, color: "text-amber-400", bg: "bg-amber-400/10" },
  { name: "Offers", value: "2", icon: CheckCircle2, color: "text-emerald-400", bg: "bg-emerald-400/10" },
  { name: "Rejections", value: "8", icon: XCircle, color: "text-rose-400", bg: "bg-rose-400/10" },
];

export default function Dashboard() {
  return (
    <AppLayout>
      <header className="mb-10">
        <h1 className="text-4xl font-extrabold tracking-tight text-white mb-2">
          Dashboard
        </h1>
        <p className="text-slate-400">Welcome back! Here&apos;s where you stand in your job hunt.</p>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        {stats.map((stat) => (
          <div key={stat.name} className="glass-card p-6 rounded-3xl relative overflow-hidden group">
            <div className={cn("inline-flex p-3 rounded-2xl mb-4 group-hover:scale-110 transition-transform duration-300", stat.bg)}>
              <stat.icon className={cn("w-6 h-6", stat.color)} />
            </div>
            <p className="text-slate-400 font-medium text-sm mb-1">{stat.name}</p>
            <h3 className="text-3xl font-bold text-white">{stat.value}</h3>
            
            {/* Subtle light effect on hover */}
            <div className="absolute inset-0 bg-gradient-to-br from-white/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none" />
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 glass-card p-8 rounded-3xl">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-bold text-white">Recent Applications</h2>
            <button className="text-indigo-400 hover:text-indigo-300 text-sm font-medium transition-colors">View All</button>
          </div>
          
          <div className="space-y-4">
            {[
              { company: "Vercel", role: "Frontend Engineer", status: "Interview", date: "Oct 12" },
              { company: "OpenAI", role: "UI Designer", status: "Applied", date: "Oct 10" },
              { company: "Stripe", role: "Software Engineer", status: "Offer", date: "Oct 05" },
            ].map((app, i) => (
              <div key={i} className="flex items-center justify-between p-4 rounded-2xl bg-white/5 border border-white/5 hover:border-white/10 transition-all group">
                <div className="flex items-center gap-4">
                  <div className="w-12 h-12 rounded-xl bg-slate-800 flex items-center justify-center text-lg font-bold text-slate-400 group-hover:text-indigo-400 transition-colors">
                    {app.company[0]}
                  </div>
                  <div>
                    <h4 className="font-semibold text-white">{app.role}</h4>
                    <p className="text-sm text-slate-400">{app.company} • {app.date}</p>
                  </div>
                </div>
                <span className={cn(
                  "px-4 py-1.5 rounded-full text-xs font-bold tracking-wide uppercase",
                  app.status === "Interview" && "bg-amber-400/10 text-amber-500",
                  app.status === "Applied" && "bg-blue-400/10 text-blue-500",
                  app.status === "Offer" && "bg-emerald-400/10 text-emerald-500"
                )}>
                  {app.status}
                </span>
              </div>
            ))}
          </div>
        </div>

        <div className="glass-card p-8 rounded-3xl flex flex-col items-center justify-center text-center">
          <div className="w-20 h-20 rounded-full bg-rose-500/10 flex items-center justify-center mb-6">
            <TrendingDown className="w-10 h-10 text-rose-500" />
          </div>
          <h3 className="text-xl font-bold text-white mb-2">Rejection Rate</h3>
          <p className="text-3xl font-black text-rose-500 mb-2">33.3%</p>
          <p className="text-sm text-slate-400">Higher than last month. Keep refining your resume!</p>
          
          <button className="mt-8 w-full py-3 rounded-2xl border border-white/10 hover:bg-white/5 transition-colors font-medium text-slate-300">
            View Analysis
          </button>
        </div>
      </div>
    </AppLayout>
  );
}
