/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="banco", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  17:    */ public class Banco
/*  18:    */   extends EntidadBase
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="banco", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="banco")
/*  25:    */   @Column(name="id_banco", unique=true, nullable=false)
/*  26:    */   private int idBanco;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=true, length=200)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @Column(name="indicador_iva_sobre_comision", nullable=false)
/*  47: 59 */   private boolean indicadorIvaSobreComision = true;
/*  48:    */   @Column(name="montoLimitePagoCash", nullable=true, precision=12, scale=2)
/*  49:    */   private BigDecimal montoLimitePagoCash;
/*  50:    */   
/*  51:    */   public Banco() {}
/*  52:    */   
/*  53:    */   public Banco(int idBanco, String codigo, String nombre)
/*  54:    */   {
/*  55: 75 */     this.idBanco = idBanco;
/*  56: 76 */     this.codigo = codigo;
/*  57: 77 */     this.nombre = nombre;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 82 */     return this.idBanco;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdBanco()
/*  66:    */   {
/*  67: 91 */     return this.idBanco;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdBanco(int idBanco)
/*  71:    */   {
/*  72:101 */     this.idBanco = idBanco;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:110 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:120 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:129 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:139 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCodigo()
/*  96:    */   {
/*  97:148 */     return this.codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setCodigo(String codigo)
/* 101:    */   {
/* 102:158 */     this.codigo = codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getNombre()
/* 106:    */   {
/* 107:167 */     return this.nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setNombre(String nombre)
/* 111:    */   {
/* 112:177 */     this.nombre = nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getDescripcion()
/* 116:    */   {
/* 117:186 */     return this.descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDescripcion(String descripcion)
/* 121:    */   {
/* 122:196 */     this.descripcion = descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isActivo()
/* 126:    */   {
/* 127:205 */     return this.activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setActivo(boolean activo)
/* 131:    */   {
/* 132:215 */     this.activo = activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isPredeterminado()
/* 136:    */   {
/* 137:224 */     return this.predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPredeterminado(boolean predeterminado)
/* 141:    */   {
/* 142:234 */     this.predeterminado = predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString()
/* 146:    */   {
/* 147:239 */     return this.nombre;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getMontoLimitePagoCash()
/* 151:    */   {
/* 152:246 */     return this.montoLimitePagoCash == null ? (this.montoLimitePagoCash = new BigDecimal(10000)) : this.montoLimitePagoCash;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setMontoLimitePagoCash(BigDecimal montoLimitePagoCash)
/* 156:    */   {
/* 157:254 */     this.montoLimitePagoCash = montoLimitePagoCash;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isIndicadorIvaSobreComision()
/* 161:    */   {
/* 162:258 */     return this.indicadorIvaSobreComision;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIndicadorIvaSobreComision(boolean indicadorIvaSobreComision)
/* 166:    */   {
/* 167:262 */     this.indicadorIvaSobreComision = indicadorIvaSobreComision;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Banco
 * JD-Core Version:    0.7.0.1
 */