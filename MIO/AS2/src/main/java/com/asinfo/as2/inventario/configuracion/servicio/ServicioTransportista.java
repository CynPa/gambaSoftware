package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.entities.Zona;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTransportista
{
  public abstract void guardar(Transportista paramTransportista);
  
  public abstract void eliminar(Transportista paramTransportista);
  
  public abstract Transportista buscarPorId(Integer paramInteger);
  
  public abstract List<Transportista> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Transportista> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void actualizarEmpresaTransportista(Empresa paramEmpresa);
  
  public abstract boolean verificarTransportista(int paramInt, String paramString);
  
  public abstract Transportista actualizarTransportista(int paramInt, String paramString);
  
  public abstract void actuzalizaTransportista(Date paramDate, List<Zona> paramList, Transportista paramTransportista, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract List<Zona> obtenerZonaAsignada(int paramInt);
  
  public abstract Transportista cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista
 * JD-Core Version:    0.7.0.1
 */