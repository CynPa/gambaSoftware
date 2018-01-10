package com.asinfo.as2.financiero.activos.reportes.servicio;

import com.asinfo.as2.entities.ActivoFijo;
import com.asinfo.as2.entities.CategoriaActivo;
import com.asinfo.as2.entities.SubcategoriaActivo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteActivoFijo
{
  public abstract List getReporteActivoFijo(int paramInt);
  
  public abstract List<Object[]> getReporteActivoFijoNECVSNIIF(int paramInt1, int paramInt2, CategoriaActivo paramCategoriaActivo, ActivoFijo paramActivoFijo, int paramInt3, boolean paramBoolean);
  
  public abstract List<Object[]> listaActivoFijoFechas(Date paramDate1, Date paramDate2, CategoriaActivo paramCategoriaActivo, SubcategoriaActivo paramSubcategoriaActivo, int paramInt, boolean paramBoolean);
  
  public abstract List<SubcategoriaActivo> listaSubcategoriaActivo(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteActivoFijo
 * JD-Core Version:    0.7.0.1
 */