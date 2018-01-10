/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.JoinColumn;
/* 10:   */ import javax.persistence.ManyToOne;
/* 11:   */ import javax.persistence.Table;
/* 12:   */ import javax.persistence.TableGenerator;
/* 13:   */ import javax.validation.constraints.NotNull;
/* 14:   */ 
/* 15:   */ @Entity
/* 16:   */ @Table(name="proceso_organizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_proceso", "id_organizacion"})})
/* 17:   */ public class ProcesoOrganizacion
/* 18:   */   extends EntidadBase
/* 19:   */ {
/* 20:   */   private static final long serialVersionUID = -2984468711000714833L;
/* 21:   */   @Id
/* 22:   */   @TableGenerator(name="proceso_organizacion", initialValue=0, allocationSize=50)
/* 23:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="proceso_organizacion")
/* 24:   */   @Column(name="id_proceso_organizacion")
/* 25:   */   private int idProcesoOrganizacion;
/* 26:   */   @ManyToOne
/* 27:   */   @JoinColumn(name="id_proceso", nullable=false)
/* 28:   */   @NotNull
/* 29:   */   private EntidadProceso entidadProceso;
/* 30:   */   @ManyToOne
/* 31:   */   @JoinColumn(name="id_organizacion", nullable=false)
/* 32:   */   @NotNull
/* 33:   */   private Organizacion organizacion;
/* 34:   */   
/* 35:   */   public int getId()
/* 36:   */   {
/* 37:72 */     return this.idProcesoOrganizacion;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Organizacion getOrganizacion()
/* 41:   */   {
/* 42:76 */     return this.organizacion;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setOrganizacion(Organizacion organizacion)
/* 46:   */   {
/* 47:80 */     this.organizacion = organizacion;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getIdProcesoOrganizacion()
/* 51:   */   {
/* 52:84 */     return this.idProcesoOrganizacion;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setIdProcesoOrganizacion(int idProcesoOrganizacion)
/* 56:   */   {
/* 57:88 */     this.idProcesoOrganizacion = idProcesoOrganizacion;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public EntidadProceso getEntidadProceso()
/* 61:   */   {
/* 62:92 */     return this.entidadProceso;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setEntidadProceso(EntidadProceso entidadProceso)
/* 66:   */   {
/* 67:96 */     this.entidadProceso = entidadProceso;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProcesoOrganizacion
 * JD-Core Version:    0.7.0.1
 */