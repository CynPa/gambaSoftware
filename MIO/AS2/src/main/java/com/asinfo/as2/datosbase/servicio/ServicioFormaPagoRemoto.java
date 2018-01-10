package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.FormaPago;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioFormaPagoRemoto
{
  public abstract void guardar(FormaPago paramFormaPago);
  
  public abstract void eliminar(FormaPago paramFormaPago);
  
  public abstract FormaPago buscarPorId(Integer paramInteger);
  
  public abstract List<FormaPago> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FormaPago> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract FormaPago buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
  
  public abstract FormaPago cargarDetalle(int paramInt);
  
  public abstract FormaPago devuelveFormaPagoPredeterminada();
  
  public abstract FormaPago buscarFormaPago(Map<String, String> paramMap)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioFormaPagoRemoto
 * JD-Core Version:    0.7.0.1
 */