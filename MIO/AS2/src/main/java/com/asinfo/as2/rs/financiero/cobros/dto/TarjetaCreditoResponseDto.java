/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class TarjetaCreditoResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private Integer idOrganizacion;
/*  11:    */   private Integer idSucursal;
/*  12:    */   private Integer idTarjetaCredito;
/*  13:    */   private String nombre;
/*  14:    */   private String nombreTipoTarjetaCredito;
/*  15: 20 */   private List<PlanTarjetaCreditoResponseDto> listaPlanTarjetaCredito = new ArrayList();
/*  16: 22 */   private int hashCode = 0;
/*  17: 24 */   private boolean activo = true;
/*  18:    */   
/*  19:    */   public int getHashCode()
/*  20:    */   {
/*  21: 27 */     this.hashCode = hashCode();
/*  22: 28 */     return this.hashCode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int hashCode()
/*  26:    */   {
/*  27: 33 */     int hash = 1;
/*  28: 34 */     hash += hash * 44 + (this.idOrganizacion + "").hashCode();
/*  29: 35 */     hash += hash * 25 + (this.idTarjetaCredito + "").hashCode();
/*  30: 36 */     hash += hash * 23 + (this.idSucursal + "").hashCode();
/*  31: 37 */     hash += hash * 11 + (this.nombre + "").hashCode();
/*  32: 38 */     hash += hash * 16 + (this.nombreTipoTarjetaCredito + "").hashCode();
/*  33: 40 */     for (PlanTarjetaCreditoResponseDto planTarjetaCreditoResponseDto : this.listaPlanTarjetaCredito) {
/*  34: 41 */       hash += hash * 28 + (planTarjetaCreditoResponseDto + "").hashCode();
/*  35:    */     }
/*  36: 44 */     return hash;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isActivo()
/*  40:    */   {
/*  41: 48 */     return this.activo;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setActivo(boolean activo)
/*  45:    */   {
/*  46: 52 */     this.activo = activo;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Integer getIdOrganizacion()
/*  50:    */   {
/*  51: 56 */     return this.idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  55:    */   {
/*  56: 60 */     this.idOrganizacion = idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Integer getIdSucursal()
/*  60:    */   {
/*  61: 64 */     return this.idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSucursal(Integer idSucursal)
/*  65:    */   {
/*  66: 68 */     this.idSucursal = idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getNombre()
/*  70:    */   {
/*  71: 72 */     return this.nombre;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setNombre(String nombre)
/*  75:    */   {
/*  76: 76 */     this.nombre = nombre;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<PlanTarjetaCreditoResponseDto> getListaPlanTarjetaCredito()
/*  80:    */   {
/*  81: 80 */     return this.listaPlanTarjetaCredito;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setListaPlanTarjetaCredito(List<PlanTarjetaCreditoResponseDto> listaPlanTarjetaCredito)
/*  85:    */   {
/*  86: 84 */     this.listaPlanTarjetaCredito = listaPlanTarjetaCredito;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setHashCode(int hashCode)
/*  90:    */   {
/*  91: 88 */     this.hashCode = hashCode;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Integer getIdTarjetaCredito()
/*  95:    */   {
/*  96: 92 */     return this.idTarjetaCredito;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdTarjetaCredito(Integer idTarjetaCredito)
/* 100:    */   {
/* 101: 96 */     this.idTarjetaCredito = idTarjetaCredito;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getNombreTipoTarjetaCredito()
/* 105:    */   {
/* 106:100 */     return this.nombreTipoTarjetaCredito;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setNombreTipoTarjetaCredito(String nombreTipoTarjetaCredito)
/* 110:    */   {
/* 111:104 */     this.nombreTipoTarjetaCredito = nombreTipoTarjetaCredito;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.TarjetaCreditoResponseDto
 * JD-Core Version:    0.7.0.1
 */