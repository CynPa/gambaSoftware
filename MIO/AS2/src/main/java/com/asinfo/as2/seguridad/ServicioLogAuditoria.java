package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.seguridad.LogAuditoria;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLogAuditoria
{
  public abstract List<LogAuditoria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contar();
  
  public abstract List<LogAuditoria> obtenerAuditoriaFiltrado(String paramString1, String paramString2, Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<Object[]> listaReporteLogAuditoria(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioLogAuditoria
 * JD-Core Version:    0.7.0.1
 */