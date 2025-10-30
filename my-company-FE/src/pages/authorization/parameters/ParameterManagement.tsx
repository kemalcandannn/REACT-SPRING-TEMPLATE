import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/use-toast";
import { RotateCcw } from "lucide-react"; // 🔄 ikon

interface Parameter {
    id: string;
    code: string;
    type: "NUMERIC" | "STATUS" | string;
    value: string;
    createdAt: string;
}

const ParameterManagement: React.FC = () => {
    const [parameters, setParameters] = useState<Parameter[]>([]);
    const [originalParameters, setOriginalParameters] = useState<Parameter[]>([]);
    const [loading, setLoading] = useState(false);
    const [refreshing, setRefreshing] = useState(false);
    const [unsavedChanges, setUnsavedChanges] = useState(false);
    const [searchTerm, setSearchTerm] = useState("");

    // 🔹 Sayfadan ayrılma uyarısı
    useEffect(() => {
        const handleBeforeUnload = (e: BeforeUnloadEvent) => {
            if (unsavedChanges) {
                e.preventDefault();
                e.returnValue = "Kaydedilmemiş değişiklikleriniz var. Çıkmak istiyor musunuz?";
            }
        };
        window.addEventListener("beforeunload", handleBeforeUnload);
        return () => window.removeEventListener("beforeunload", handleBeforeUnload);
    }, [unsavedChanges]);

    // 🔹 Verileri backend'den çek
    const fetchParameters = async () => {
        try {
            setRefreshing(true);
            const res = await axios.get("/api/parameters");
            setParameters(res.data);
            setOriginalParameters(res.data);
            setUnsavedChanges(false);
            toast({ description: "Parametre listesi yenilendi ✅" });
        } catch {
            toast({ description: "Parametreler yüklenemedi", variant: "destructive" });
        } finally {
            setRefreshing(false);
        }
    };

    useEffect(() => {
        fetchParameters();
    }, []);

    const handleChange = (index: number, newValue: string) => {
        const updated = [...parameters];
        updated[index].value = newValue;
        setParameters(updated);
        setUnsavedChanges(true);
    };

    // 🔹 Filtreleme (kod bazlı)
    const filteredParameters = useMemo(() => {
        return parameters.filter((p) =>
            p.code.toLowerCase().includes(searchTerm.toLowerCase().trim())
        );
    }, [parameters, searchTerm]);

    // 🔹 Sadece değişen satırları al
    const getChangedParameters = (): Parameter[] => {
        return parameters.filter((p) => {
            const original = originalParameters.find((o) => o.id === p.id);
            return original && original.value !== p.value;
        });
    };

    const handleSave = async () => {
        const changedParams = getChangedParameters();
        if (changedParams.length === 0) {
            toast({ description: "Değişiklik yok, kaydedilecek bir şey bulunamadı." });
            return;
        }

        try {
            setLoading(true);
            await axios.put("/api/parameters", changedParams);
            toast({
                description: `${changedParams.length} parametre başarıyla güncellendi ✅`,
            });

            // Orijinalleri güncelle
            const updated = parameters.map((p) => {
                const changed = changedParams.find((c) => c.id === p.id);
                return changed ? { ...p, value: changed.value } : p;
            });

            setOriginalParameters(updated);
            setParameters(updated);
            setUnsavedChanges(false);
        } catch (err) {
            toast({
                description: "Güncelleme sırasında hata oluştu",
                variant: "destructive",
            });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="max-w-6xl mx-auto mt-10">
            <Card>
                <CardHeader>
                    <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
                        <CardTitle className="text-xl font-bold">Sistem Parametreleri</CardTitle>

                        <div className="flex items-center gap-2">
                            <Input
                                placeholder="Parametre koduna göre ara..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                className="w-64"
                            />

                            <Button
                                onClick={fetchParameters}
                                variant="outline"
                                size="sm"
                                disabled={refreshing}
                            >
                                {refreshing ? (
                                    <span className="animate-spin mr-1">
                                        <RotateCcw size={16} />
                                    </span>
                                ) : (
                                    <RotateCcw size={16} className="mr-1" />
                                )}
                                Yenile
                            </Button>

                            {unsavedChanges && (
                                <span className="text-amber-600 text-sm animate-pulse">
                                    💾 Kaydedilmemiş değişiklikler var
                                </span>
                            )}
                        </div>
                    </div>
                </CardHeader>

                <CardContent>
                    <div className="overflow-x-auto rounded-lg border">
                        <table className="min-w-full border-collapse text-sm">
                            <thead className="bg-gray-100 text-gray-700">
                                <tr>
                                    <th className="p-3 text-left font-medium">Kod</th>
                                    <th className="p-3 text-left font-medium">Tür</th>
                                    <th className="p-3 text-left font-medium">Değer</th>
                                    <th className="p-3 text-left font-medium">Oluşturulma</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredParameters.length === 0 ? (
                                    <tr>
                                        <td colSpan={4} className="text-center py-6 text-gray-500">
                                            Aramanızla eşleşen parametre bulunamadı.
                                        </td>
                                    </tr>
                                ) : (
                                    filteredParameters.map((p, i) => (
                                        <tr key={p.id} className="border-b hover:bg-gray-50">
                                            <td className="p-3 font-mono text-xs">{p.code}</td>
                                            <td className="p-3">{p.type}</td>
                                            <td className="p-3">
                                                {p.type === "STATUS" ? (
                                                    <select
                                                        className="border rounded-md px-2 py-1 text-sm"
                                                        value={p.value}
                                                        onChange={(e) =>
                                                            handleChange(parameters.indexOf(p), e.target.value)
                                                        }
                                                    >
                                                        <option value="ACTIVE">ACTIVE</option>
                                                        <option value="PASSIVE">PASSIVE</option>
                                                    </select>
                                                ) : (
                                                    <Input
                                                        type="text"
                                                        value={p.value}
                                                        onChange={(e) =>
                                                            handleChange(parameters.indexOf(p), e.target.value)
                                                        }
                                                        className="text-sm"
                                                    />
                                                )}
                                            </td>
                                            <td className="p-3 text-gray-500 text-xs">
                                                {new Date(p.createdAt).toLocaleString("tr-TR")}
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    </div>

                    <div className="flex justify-end mt-6">
                        <Button
                            onClick={handleSave}
                            disabled={loading || !unsavedChanges}
                            variant={unsavedChanges ? "default" : "secondary"}
                        >
                            {loading
                                ? "Kaydediliyor..."
                                : unsavedChanges
                                    ? "Kaydet"
                                    : "Kaydedildi"}
                        </Button>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default ParameterManagement;
