/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Periodicidad;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="reporteador", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  23:    */ public class Reporteador
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="reporteador", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="reporteador")
/*  30:    */   @Column(name="id_reporteador")
/*  31:    */   private int idReporteador;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="nombre", nullable=false, length=100)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=100)
/*  39:    */   private String nombre;
/*  40:    */   @Enumerated(EnumType.ORDINAL)
/*  41:    */   @Column(name="periodicidad", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Periodicidad periodicidad;
/*  44:    */   @Column(name="fichero_reporte", nullable=false, length=100)
/*  45:    */   @NotNull
/*  46:    */   @ColumnDefault("''")
/*  47:    */   @Size(min=2, max=100)
/*  48:    */   private String ficheroReporte;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   @ColumnDefault("'1'")
/*  52: 78 */   private boolean activo = true;
/*  53:    */   @Column(name="predeterminado", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   @ColumnDefault("'0'")
/*  56:    */   private boolean predeterminado;
/*  57:    */   @Column(name="descripcion", length=200, nullable=true)
/*  58:    */   @Size(max=200)
/*  59:    */   private String descripcion;
/*  60:    */   @OneToMany(mappedBy="reporteador", fetch=FetchType.LAZY)
/*  61: 92 */   private List<DetalleReporteador> listaDetalleReporteador = new ArrayList();
/*  62:    */   @OneToMany(mappedBy="reporteador", fetch=FetchType.LAZY)
/*  63: 95 */   private List<DetalleReporteadorVariable> listaDetalleReporteadorVariable = new ArrayList();
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67:100 */     return this.idReporteador;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdReporteador()
/*  71:    */   {
/*  72:104 */     return this.idReporteador;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdReporteador(int idReporteador)
/*  76:    */   {
/*  77:108 */     this.idReporteador = idReporteador;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:112 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:116 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:120 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:124 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:128 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:132 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Periodicidad getPeriodicidad()
/* 111:    */   {
/* 112:136 */     return this.periodicidad;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setPeriodicidad(Periodicidad periodicidad)
/* 116:    */   {
/* 117:140 */     this.periodicidad = periodicidad;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:144 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:148 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<DetalleReporteador> getListaDetalleReporteador()
/* 131:    */   {
/* 132:152 */     return this.listaDetalleReporteador;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaDetalleReporteador(List<DetalleReporteador> listaDetalleReporteador)
/* 136:    */   {
/* 137:156 */     this.listaDetalleReporteador = listaDetalleReporteador;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isActivo()
/* 141:    */   {
/* 142:160 */     return this.activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setActivo(boolean activo)
/* 146:    */   {
/* 147:164 */     this.activo = activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isPredeterminado()
/* 151:    */   {
/* 152:168 */     return this.predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPredeterminado(boolean predeterminado)
/* 156:    */   {
/* 157:172 */     this.predeterminado = predeterminado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<DetalleReporteadorVariable> getListaDetalleReporteadorVariable()
/* 161:    */   {
/* 162:176 */     return this.listaDetalleReporteadorVariable;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaDetalleReporteadorVariable(List<DetalleReporteadorVariable> listaDetalleReporteadorVariable)
/* 166:    */   {
/* 167:180 */     this.listaDetalleReporteadorVariable = listaDetalleReporteadorVariable;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getFicheroReporte()
/* 171:    */   {
/* 172:184 */     return this.ficheroReporte;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFicheroReporte(String ficheroReporte)
/* 176:    */   {
/* 177:188 */     this.ficheroReporte = ficheroReporte;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Reporteador
 * JD-Core Version:    0.7.0.1
 */