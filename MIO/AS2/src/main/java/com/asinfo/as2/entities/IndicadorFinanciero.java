/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="indicador_financiero", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  20:    */ public class IndicadorFinanciero
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="indicador_financiero", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="indicador_financiero")
/*  28:    */   @Column(name="id_indicador_financiero")
/*  29:    */   private int idIndicadorFinanciero;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   @NotNull
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="codigo", nullable=false, length=10)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=10)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @Size(max=50)
/*  42:    */   @NotNull
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", nullable=true, length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="estado", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private boolean estado;
/*  50:    */   @Column(name="expresion", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private String expresion;
/*  53:    */   @Transient
/*  54: 84 */   private BigDecimal valor = BigDecimal.ZERO;
/*  55:    */   @Transient
/*  56:    */   private List<Variable> listaVariablesIndicadoresFinancieros;
/*  57:    */   @Transient
/*  58:    */   private Date fecha;
/*  59:    */   
/*  60:    */   public IndicadorFinanciero() {}
/*  61:    */   
/*  62:    */   public IndicadorFinanciero(int idIndicadorFinanciero, String codigo, String nombre, String descripcion, String expresion)
/*  63:    */   {
/*  64:106 */     this.idIndicadorFinanciero = idIndicadorFinanciero;
/*  65:107 */     this.codigo = codigo;
/*  66:108 */     this.nombre = nombre;
/*  67:109 */     this.descripcion = descripcion;
/*  68:110 */     this.expresion = expresion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getId()
/*  72:    */   {
/*  73:115 */     return getIdIndicadorFinanciero();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdIndicadorFinanciero()
/*  77:    */   {
/*  78:119 */     return this.idIndicadorFinanciero;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdIndicadorFinanciero(int idIndicadorFinanciero)
/*  82:    */   {
/*  83:123 */     this.idIndicadorFinanciero = idIndicadorFinanciero;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:127 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:131 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:135 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:139 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getCodigo()
/* 107:    */   {
/* 108:143 */     return this.codigo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCodigo(String codigo)
/* 112:    */   {
/* 113:147 */     this.codigo = codigo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getNombre()
/* 117:    */   {
/* 118:151 */     return this.nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNombre(String nombre)
/* 122:    */   {
/* 123:155 */     this.nombre = nombre;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getDescripcion()
/* 127:    */   {
/* 128:159 */     return this.descripcion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDescripcion(String descripcion)
/* 132:    */   {
/* 133:163 */     this.descripcion = descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isEstado()
/* 137:    */   {
/* 138:167 */     return this.estado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setEstado(boolean estado)
/* 142:    */   {
/* 143:171 */     this.estado = estado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getExpresion()
/* 147:    */   {
/* 148:175 */     return this.expresion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setExpresion(String expresion)
/* 152:    */   {
/* 153:179 */     this.expresion = expresion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getValor()
/* 157:    */   {
/* 158:183 */     return this.valor;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setValor(BigDecimal valor)
/* 162:    */   {
/* 163:187 */     this.valor = valor;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public List<Variable> getListaVariablesIndicadoresFinancieros()
/* 167:    */   {
/* 168:191 */     return this.listaVariablesIndicadoresFinancieros;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaVariablesIndicadoresFinancieros(List<Variable> listaVariablesIndicadoresFinancieros)
/* 172:    */   {
/* 173:196 */     this.listaVariablesIndicadoresFinancieros = listaVariablesIndicadoresFinancieros;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Date getFecha()
/* 177:    */   {
/* 178:200 */     return this.fecha;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFecha(Date fecha)
/* 182:    */   {
/* 183:204 */     this.fecha = fecha;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.IndicadorFinanciero
 * JD-Core Version:    0.7.0.1
 */