package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLote
{
  public abstract void guardar(Lote paramLote)
    throws ExcepcionAS2Inventario, AS2Exception;
  
  public abstract void eliminar(Lote paramLote);
  
  public abstract Lote buscarPorId(int paramInt);
  
  public abstract List<Lote> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Lote> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Lote cargarDetalle(int paramInt);
  
  public abstract List<Lote> autocompletarLote(Producto paramProducto, String paramString);
  
  public abstract List<Lote> autocompletarLote(String paramString);
  
  public abstract Lote buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
  
  public abstract Lote buscarPorCodigo(String paramString, Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract Lote buscarPorCodigoIndicadorMovimiento(String paramString, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract Lote buscarPorCodigoProductoProveedor(String paramString, Producto paramProducto, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract Lote crearLoteInterno(int paramInt, Producto paramProducto, String paramString, boolean paramBoolean)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract Lote crearLote(int paramInt, Producto paramProducto, String paramString, boolean paramBoolean1, Empresa paramEmpresa, Date paramDate1, Date paramDate2, boolean paramBoolean2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract Lote getAtributosLote(int paramInt1, int paramInt2);
  
  public abstract void actualizarAtributoEntidad(Lote paramLote, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioLote
 * JD-Core Version:    0.7.0.1
 */