package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.CentroCosto;
import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteLibroAuxiliar
{
  public abstract List<DetalleAsiento> getReporteLibroAuxiliar(int paramInt1, Date paramDate1, Date paramDate2, boolean paramBoolean, int paramInt2, CentroCosto paramCentroCosto, int paramInt3)
    throws ExcepcionAS2Financiero;
  
  public abstract List<DetalleAsiento> getReporteLibroAuxiliar(List<CuentaContable> paramList, Date paramDate1, Date paramDate2, boolean paramBoolean, int paramInt1, String paramString1, String paramString2, int paramInt2)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteLibroAuxiliar
 * JD-Core Version:    0.7.0.1
 */