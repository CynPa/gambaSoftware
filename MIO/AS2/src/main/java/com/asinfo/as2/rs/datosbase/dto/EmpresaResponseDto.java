/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   4:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ 
/*  10:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  11:    */ public class EmpresaResponseDto
/*  12:    */   extends ClienteResponseDto
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private Boolean indicadorCliente;
/*  16:    */   private Boolean indicadorProveedor;
/*  17:    */   private Integer idProveedor;
/*  18:    */   private String descripcion;
/*  19:    */   private CatalogoGenericoResponseDto categoriaEmpresa;
/*  20:    */   private TipoEmpresa tipoEmpresa;
/*  21:    */   private BigDecimal latitud;
/*  22:    */   private BigDecimal longitud;
/*  23: 24 */   private List<ContactoResponseDto> listaContactoEmpresa = new ArrayList();
/*  24: 26 */   private int hashCode = 0;
/*  25:    */   
/*  26:    */   public int getHashCode()
/*  27:    */   {
/*  28: 29 */     this.hashCode = hashCode();
/*  29: 30 */     return this.hashCode;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int hashCode()
/*  33:    */   {
/*  34: 35 */     int hash = super.hashCode();
/*  35:    */     
/*  36: 37 */     hash += hash * 17 + (this.indicadorCliente + "").hashCode();
/*  37: 38 */     hash += hash * 27 + (this.indicadorProveedor + "").hashCode();
/*  38: 39 */     hash += hash * 37 + (this.idProveedor + "").hashCode();
/*  39: 40 */     hash += hash * 47 + (this.descripcion + "").hashCode();
/*  40: 41 */     hash += hash * 47 + (this.tipoEmpresa + "").hashCode();
/*  41: 42 */     hash += hash * 47 + (this.categoriaEmpresa != null ? this.categoriaEmpresa.getHashCode() : 0);
/*  42: 43 */     hash += hash * 47 + (this.latitud + "").hashCode();
/*  43: 44 */     hash += hash * 47 + (this.longitud + "").hashCode();
/*  44: 46 */     for (ContactoResponseDto contactoEmpresaResponseDto : this.listaContactoEmpresa) {
/*  45: 47 */       hash += contactoEmpresaResponseDto.getHashCode();
/*  46:    */     }
/*  47: 49 */     return hash;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Boolean getIndicadorCliente()
/*  51:    */   {
/*  52: 53 */     return this.indicadorCliente;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIndicadorCliente(Boolean indicadorCliente)
/*  56:    */   {
/*  57: 57 */     this.indicadorCliente = indicadorCliente;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Boolean getIndicadorProveedor()
/*  61:    */   {
/*  62: 61 */     return this.indicadorProveedor;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIndicadorProveedor(Boolean indicadorProveedor)
/*  66:    */   {
/*  67: 65 */     this.indicadorProveedor = indicadorProveedor;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Integer getIdProveedor()
/*  71:    */   {
/*  72: 69 */     return this.idProveedor;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdProveedor(Integer idProveedor)
/*  76:    */   {
/*  77: 73 */     this.idProveedor = idProveedor;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<ContactoResponseDto> getListaContactoEmpresa()
/*  81:    */   {
/*  82: 77 */     return this.listaContactoEmpresa;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setListaContactoEmpresa(List<ContactoResponseDto> listaContactoEmpresa)
/*  86:    */   {
/*  87: 81 */     this.listaContactoEmpresa = listaContactoEmpresa;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDescripcion()
/*  91:    */   {
/*  92: 85 */     return this.descripcion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setDescripcion(String descripcion)
/*  96:    */   {
/*  97: 89 */     this.descripcion = descripcion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public CatalogoGenericoResponseDto getCategoriaEmpresa()
/* 101:    */   {
/* 102: 93 */     return this.categoriaEmpresa;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCategoriaEmpresa(CatalogoGenericoResponseDto categoriaEmpresa)
/* 106:    */   {
/* 107: 97 */     this.categoriaEmpresa = categoriaEmpresa;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setHashCode(int hashCode)
/* 111:    */   {
/* 112:101 */     this.hashCode = hashCode;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public TipoEmpresa getTipoEmpresa()
/* 116:    */   {
/* 117:105 */     return this.tipoEmpresa;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setTipoEmpresa(TipoEmpresa tipoEmpresa)
/* 121:    */   {
/* 122:109 */     this.tipoEmpresa = tipoEmpresa;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BigDecimal getLatitud()
/* 126:    */   {
/* 127:113 */     return this.latitud;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setLatitud(BigDecimal latitud)
/* 131:    */   {
/* 132:117 */     this.latitud = latitud;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public BigDecimal getLongitud()
/* 136:    */   {
/* 137:121 */     return this.longitud;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setLongitud(BigDecimal longitud)
/* 141:    */   {
/* 142:125 */     this.longitud = longitud;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.EmpresaResponseDto
 * JD-Core Version:    0.7.0.1
 */