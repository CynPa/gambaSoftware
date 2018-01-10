package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.DocumentoGastoImportacion;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.GastoImportacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoGastoImportacion
{
  public abstract void guardar(DocumentoGastoImportacion paramDocumentoGastoImportacion);
  
  public abstract void eliminar(DocumentoGastoImportacion paramDocumentoGastoImportacion);
  
  public abstract DocumentoGastoImportacion buscarPorId(int paramInt);
  
  public abstract List<DocumentoGastoImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DocumentoGastoImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract DocumentoGastoImportacion cargarDetalle(int paramInt);
  
  public abstract List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(Documento paramDocumento, boolean paramBoolean);
  
  public abstract List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(FacturaProveedor paramFacturaProveedor, boolean paramBoolean, List<GastoImportacion> paramList);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioDocumentoGastoImportacion
 * JD-Core Version:    0.7.0.1
 */