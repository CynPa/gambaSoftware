package com.asinfo.as2.produccion.reportes.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.produccion.PlanProduccion;
import java.util.Date;
import java.util.List;

public abstract interface ServicioReportesProduccion
{
  public abstract List<Object[]> reporteOrdenFabricacion(Date paramDate, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract List<Object[]> listaMateriaPrima(PlanProduccion paramPlanProduccion, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract List<Object[]> listaRendimientoMateriales(CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Date paramDate1, Date paramDate2, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.servicio.ServicioReportesProduccion
 * JD-Core Version:    0.7.0.1
 */