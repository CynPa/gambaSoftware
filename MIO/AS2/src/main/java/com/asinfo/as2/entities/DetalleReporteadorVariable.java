/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*   4:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*   5:    */ import com.asinfo.as2.utils.validacion.email.SoloTexto;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_reporteador_variable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_reporteador", "codigo"})})
/*  26:    */ public class DetalleReporteadorVariable
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="detalle_reporteador_variable", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_reporteador_variable")
/*  33:    */   @Column(name="id_detalle_reporteador_variable")
/*  34:    */   private int idDetalleReporteadorVariable;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="codigo", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   @SoloTexto
/*  44:    */   @Size(min=2, max=20)
/*  45:    */   private String codigo;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_reporteador", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Reporteador reporteador;
/*  50:    */   @Column(name="indicador_formula", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private boolean indicadorFormula;
/*  53:    */   @Enumerated(EnumType.ORDINAL)
/*  54:    */   @Column(name="tipo_calculo", nullable=true)
/*  55:    */   private TipoCalculo tipoCalculo;
/*  56:    */   @Enumerated(EnumType.ORDINAL)
/*  57:    */   @Column(name="valores_calculo", nullable=true)
/*  58:    */   private ValoresCalculo valoresCalculo;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  61:    */   private CuentaContable cuentaContable;
/*  62:    */   @Column(name="expresion", nullable=true)
/*  63:    */   @Size(max=500)
/*  64:    */   private String expresion;
/*  65:    */   @Column(name="descripcion", nullable=true)
/*  66:    */   @Size(max=500)
/*  67:    */   private String descripcion;
/*  68:    */   @Transient
/*  69:101 */   private List<DetalleReporteadorVariable> listaDetalleVariablesExpresion = new ArrayList();
/*  70:    */   
/*  71:    */   public int getId()
/*  72:    */   {
/*  73:106 */     return this.idDetalleReporteadorVariable;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdDetalleReporteadorVariable()
/*  77:    */   {
/*  78:110 */     return this.idDetalleReporteadorVariable;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdDetalleReporteadorVariable(int idDetalleReporteadorVariable)
/*  82:    */   {
/*  83:114 */     this.idDetalleReporteadorVariable = idDetalleReporteadorVariable;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrganizacion()
/*  87:    */   {
/*  88:118 */     return this.idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:122 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdSucursal()
/*  97:    */   {
/*  98:126 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(int idSucursal)
/* 102:    */   {
/* 103:130 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isIndicadorFormula()
/* 107:    */   {
/* 108:134 */     return this.indicadorFormula;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIndicadorFormula(boolean indicadorFormula)
/* 112:    */   {
/* 113:138 */     this.indicadorFormula = indicadorFormula;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoCalculo getTipoCalculo()
/* 117:    */   {
/* 118:142 */     return this.tipoCalculo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoCalculo(TipoCalculo tipoCalculo)
/* 122:    */   {
/* 123:146 */     this.tipoCalculo = tipoCalculo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ValoresCalculo getValoresCalculo()
/* 127:    */   {
/* 128:150 */     return this.valoresCalculo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setValoresCalculo(ValoresCalculo valoresCalculo)
/* 132:    */   {
/* 133:154 */     this.valoresCalculo = valoresCalculo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public CuentaContable getCuentaContable()
/* 137:    */   {
/* 138:158 */     return this.cuentaContable;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 142:    */   {
/* 143:162 */     this.cuentaContable = cuentaContable;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getExpresion()
/* 147:    */   {
/* 148:166 */     return this.expresion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setExpresion(String expresion)
/* 152:    */   {
/* 153:170 */     this.expresion = expresion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Reporteador getReporteador()
/* 157:    */   {
/* 158:174 */     return this.reporteador;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setReporteador(Reporteador reporteador)
/* 162:    */   {
/* 163:178 */     this.reporteador = reporteador;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getCodigo()
/* 167:    */   {
/* 168:182 */     return this.codigo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setCodigo(String codigo)
/* 172:    */   {
/* 173:186 */     this.codigo = codigo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getDescripcion()
/* 177:    */   {
/* 178:190 */     return this.descripcion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDescripcion(String descripcion)
/* 182:    */   {
/* 183:194 */     this.descripcion = descripcion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<DetalleReporteadorVariable> getListaDetalleVariablesExpresion()
/* 187:    */   {
/* 188:198 */     return this.listaDetalleVariablesExpresion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaDetalleVariablesExpresion(List<DetalleReporteadorVariable> listaDetalleVariablesExpresion)
/* 192:    */   {
/* 193:202 */     this.listaDetalleVariablesExpresion = listaDetalleVariablesExpresion;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleReporteadorVariable
 * JD-Core Version:    0.7.0.1
 */