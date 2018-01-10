package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DetalleListaDescuentos;
import com.asinfo.as2.entities.ListaDescuentos;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.excepciones.AS2Exception;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioListaDescuentos
{
  public abstract void guardar(ListaDescuentos paramListaDescuentos)
    throws AS2Exception;
  
  public abstract void eliminar(ListaDescuentos paramListaDescuentos);
  
  public abstract ListaDescuentos buscarPorId(int paramInt);
  
  public abstract List<ListaDescuentos> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract DetalleListaDescuentos getDatosListaDescuentosPorProducto(ListaDescuentos paramListaDescuentos, Producto paramProducto);
  
  public abstract ListaDescuentos cargarDetalle(int paramInt);
  
  public abstract List<ListaDescuentos> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Object[]> getReportelistaDescuentos(int paramInt);
  
  public abstract void cambioMasivoDescuento(List<DetalleListaDescuentos> paramList, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract List<DetalleListaDescuentos> listaCambioMasivoDescuento(List<ListaDescuentos> paramList, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto);
  
  public abstract void asignarProductoNoAsignados(List<ListaDescuentos> paramList, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract ListaDescuentos obtenerListaDescuentosVigente(int paramInt);
  
  public abstract BigDecimal getPorcentajeDescuentoMaximoVigente(ListaDescuentos paramListaDescuentos, Date paramDate);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos
 * JD-Core Version:    0.7.0.1
 */