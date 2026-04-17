"use client";

import { useState } from "react";
import { Sidebar } from "./Sidebar";
import { motion } from "framer-motion";
import { AddApplicationModal } from "../modals/AddApplicationModal";
import { useRouter } from "next/navigation";

export function AppLayout({ children }: { children: React.ReactNode }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const router = useRouter();

  return (
    <div className="flex min-h-screen bg-slate-950 overflow-hidden">
      <Sidebar onAddNew={() => setIsModalOpen(true)} />
      <main className="flex-1 ml-64 p-8 overflow-y-auto h-screen relative">
        {/* Background Gradients */}
        <div className="fixed inset-0 pointer-events-none overflow-hidden">
          <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-indigo-500/10 blur-[120px] rounded-full" />
          <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] bg-violet-500/10 blur-[120px] rounded-full" />
        </div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, ease: "easeOut" }}
          className="relative z-10 max-w-7xl mx-auto"
        >
          {children}
        </motion.div>

        <AddApplicationModal 
          isOpen={isModalOpen} 
          onClose={() => setIsModalOpen(false)} 
          onSuccess={() => {
            router.refresh();
          }} 
        />
      </main>
    </div>
  );
}
